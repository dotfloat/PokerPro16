package org.gruppe2.game.controller;

import java.time.LocalDateTime;
import java.util.*;

import org.gruppe2.game.Action;
import org.gruppe2.game.Card;
import org.gruppe2.game.Player;
import org.gruppe2.game.PokerLog;
import org.gruppe2.game.PossibleActions;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.event.CommunityCardsEvent;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.event.PlayerLeaveEvent;
import org.gruppe2.game.event.PlayerPaysBlind;
import org.gruppe2.game.event.PlayerPostActionEvent;
import org.gruppe2.game.event.PlayerPreActionEvent;
import org.gruppe2.game.event.PlayerWonEvent;
import org.gruppe2.game.event.QuitEvent;
import org.gruppe2.game.event.RoundEndEvent;
import org.gruppe2.game.event.RoundStartEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Message;

public class RoundController extends AbstractController {
    @Helper
    private RoundHelper round;
    @Helper
    private GameHelper game;

    private LocalDateTime timeToStart = null;
    private Player player = null;
    private RoundPlayer roundPlayer = null;
    private UUID lastPlayerInRound = null;
    private Stack<Card> deck = new Stack<>();
    private PokerLog logger = null;
    private ArrayList<UUID> raiseStory = new ArrayList<>();

    @Override
    public void update() {
        if (round.isPlaying() && timeToStart != null) {
            if (LocalDateTime.now().isAfter(timeToStart)) {
                timeToStart = null;
                resetRound();

                if (!round.isPlaying())
                    return;

                payBlinds();
                addEvent(new RoundStartEvent());
            } else {
                return;
            }
        }

        if (round.isPlaying()) {
            if (round.getActivePlayers().size() == 1) {
                roundEnd();
                return;
            }

            // Go to next player and do shit
            if (player == null) {
                Optional<Player> op = game.findPlayerByUUID(round.getCurrentUUID());
                Optional<RoundPlayer> opr = round.findPlayerByUUID(round.getCurrentUUID());

                if (!op.isPresent() || !opr.isPresent())
                    return;

                player = op.get();
                roundPlayer = opr.get();

                addEvent(new PlayerPreActionEvent(player));

                if (player.getBank() > 0)
                    addEvent(new PlayerActionQuery(player, roundPlayer));
                else if (player.getBank() == 0)
                    player.getAction().set(new Action.Pass());
                else
                    throw new IllegalStateException("Player: " + player.getName() + " has less than 0 chips");
            }

            if (player.getAction().isDone()) {
                if (!(player.getAction().get() instanceof Action.Pass))
                    handleAction(player, roundPlayer, player.getAction().get());

                if (!(player.getAction().get() instanceof Action.Raise)
                        && ((round.getLastRaiserID() == null && player.getUUID().equals(lastPlayerInRound))
                        || player.getUUID().equals(round.getLastRaiserID()))) {
                    player.getAction().reset();
                    player = null;
                    roundPlayer = null;
                    nextRound();
                } else {
                    round.setCurrent((round.getCurrent() + 1) % round.getActivePlayers().size());
                    player.getAction().reset();
                    player = null;
                    roundPlayer = null;
                }
            }
        }
    }

    @Message
    public boolean roundStart() {
        if (!round.isPlaying()) {
            round.setPlaying(true);
            logger = new PokerLog();
            timeToStart = LocalDateTime.now().plusSeconds(3);
            return true;
        }

        return false;
    }

    @Handler
    public void onPlayerLeave(PlayerLeaveEvent event) {
        Optional<RoundPlayer> opr = round.findPlayerByUUID(event.getPlayer().getUUID());

        if (!opr.isPresent())
            return;

        RoundPlayer player = opr.get();

        if (player == null)
            return;

        if (player.getUUID().equals(this.player.getUUID())) {
            this.player = null;
            roundPlayer = null;
        }

        if (raiseStory.removeIf(uuid -> player.getUUID().equals(uuid))) {
            round.setLastRaiserID(raiseStory.get(raiseStory.size()-1));
        }

        if (round.getActivePlayers().indexOf(player) <= round.getCurrent()) {
            round.setCurrent(round.getCurrent()-1);
        }

        round.getActivePlayers().remove(player);
        game.getPlayers().remove(event.getPlayer());
    }

    private void resetRound() {
        List<Player> sortedPlayers = new ArrayList<>(game.getPlayers());
        sortedPlayers.sort((p1, p2) -> Integer.compare(p1.getTablePosition(), p2.getTablePosition()));

        List<RoundPlayer> active = round.getActivePlayers();
        active.clear();
        resetDeck();
        logger = new PokerLog();

        boolean done = false;
        for (int i = game.getButton(); !done; i++) {
            int j = (i + 1) % sortedPlayers.size();
            Player p = sortedPlayers.get(j);
            if (p.getBank() > 0)
                active.add(new RoundPlayer(p.getUUID(), deck.pop(), deck.pop()));
            done = j == game.getButton();
        }

        if (active.size() <= 1) {
            addEvent(new QuitEvent());
            round.setPlaying(false);
            return;
        }

        round.addPlayersToMap(active);

        round.setPot(0);
        round.setHighestBet(0);
        round.setCurrent(0);
        lastPlayerInRound = round.getLastActivePlayerID();
        round.resetRound();
        round.getCommunityCards().clear();
    }

    private void payBlinds() {
        int currentBigBlind = game.getBigBlind() + ((int) (game.getRoundsCompleted() * game.getBigBlind() * 0.1));
        int currentSmallBlind = game.getSmallBlind() + ((int) (game.getRoundsCompleted() * game.getSmallBlind() * 0.1));

        RoundPlayer roundPlayer = round.getActivePlayers().get(1);
        Optional<Player> op = game.findPlayerByUUID(roundPlayer.getUUID());

        if (!op.isPresent())
            throw new NoSuchElementException("Player with id: " + roundPlayer.getUUID() + " not found in the game player list");

        Player player = op.get();

        if (player.getBank() < currentBigBlind) {
            currentBigBlind = player.getBank();
            currentSmallBlind = currentBigBlind / 2;
            if (currentSmallBlind <= 0)
                currentSmallBlind = 1;
        }
        handleAction(player, roundPlayer, new Action.Blind(currentBigBlind));
        addEvent(new PlayerPaysBlind(player, roundPlayer, currentBigBlind));

        roundPlayer = round.getActivePlayers().get(0);
        op = game.findPlayerByUUID(roundPlayer.getUUID());

        if (!op.isPresent())
            throw new NoSuchElementException("Player with id: " + roundPlayer.getUUID() + " not found in the game player list");

        player = op.get();

        if (player.getBank() < currentSmallBlind)
            currentSmallBlind = player.getBank();

        handleAction(player, roundPlayer, new Action.Blind(currentSmallBlind));
        addEvent(new PlayerPaysBlind(player, roundPlayer, currentSmallBlind));
    }

    private void handleAction(Player player, RoundPlayer roundPlayer, Action action) {
        if (!legalAction(player, roundPlayer, action))
            throw new IllegalArgumentException(player.getName() + " can't do action: " + action);

        logger.recordPlayerAction(player, roundPlayer, action);

        int raise;
        if (action instanceof Action.Call) {
            raise = round.getHighestBet() - roundPlayer.getBet();
            moveChips(player, roundPlayer, roundPlayer.getBet() + raise, player.getBank() - raise, raise);
        }

        if (action instanceof Action.AllIn) {
            raise = player.getBank();
            moveChips(player, roundPlayer, roundPlayer.getBet() + raise, 0, raise);
        }

        if (action instanceof Action.Fold) {
            round.getActivePlayers().remove(round.getCurrent());
            round.setCurrent(round.getCurrent() - 1);
        }

        if (action instanceof Action.Raise) {
            raise = ((Action.Raise) action).getAmount();
            int chipsToMove = (round.getHighestBet() - roundPlayer.getBet()) + raise;
            moveChips(player, roundPlayer, round.getHighestBet() + raise, player.getBank() - chipsToMove, chipsToMove);
            round.setLastRaiserID(player.getUUID());
            round.playerRaise(player.getUUID());
            raiseStory.add(player.getUUID());
        }

        if (action instanceof Action.Blind) {
            int amount = ((Action.Blind) action).getAmount();
            moveChips(player, roundPlayer, amount, player.getBank() - amount, amount);
        }

        if (roundPlayer.getBet() > round.getHighestBet())
            round.setHighestBet(roundPlayer.getBet());

        if (!(action instanceof Action.Blind))
            addEvent(new PlayerPostActionEvent(player, roundPlayer, action));
    }

    private void moveChips(Player player, RoundPlayer roundPlayer, int playerSetBet, int playerSetBank, int addToTablePot) {
        roundPlayer.setBet(playerSetBet);
        player.setBank(playerSetBank);
        round.addToPot(addToTablePot);
    }

    private boolean legalAction(Player player, RoundPlayer roundPlayer, Action action) {
        if (!round.getActivePlayers().contains(roundPlayer))
            return false;

        PossibleActions pa = round.getPlayerOptions(player.getUUID());
        if (action instanceof Action.Check)
            return pa.canCheck();
        else if (action instanceof Action.Raise) {
            int raise = ((Action.Raise) action).getAmount();
            if (raise < 1 || raise > player.getBank() + roundPlayer.getBet() - round.getHighestBet())
                throw new IllegalArgumentException(player.getName() + " cant raise with " + ((Action.Raise) action).getAmount());
            return pa.canRaise();
        } else if (action instanceof Action.Call)
            return pa.canCall();
        else if (action instanceof Action.Fold || action instanceof Action.Blind || action instanceof Action.AllIn || action instanceof Action.Pass)
            return true;
        else
            throw new IllegalArgumentException("Not an action");
    }

    private void nextRound() {
        if (round.getRoundNum() == 3) {
            roundEnd();
        } else {
            round.nextRound();
            round.setLastRaiserID(null);
            lastPlayerInRound = round.getLastActivePlayerID();
            round.resetRaiseMap();
            round.setCurrent(0);

            if (round.getRoundNum() == 1) {
                for (int i = 0; i < 3; i++)
                    round.getCommunityCards().add(deck.pop());
            } else if (round.getRoundNum() == 2 || round.getRoundNum() == 3)
                round.getCommunityCards().add(deck.pop());

            addEvent(new CommunityCardsEvent(new ArrayList<>(round.getCommunityCards())));

            logger.incrementRound(round.getCommunityCards());
        }
    }

    private void roundEnd() {
        round.setPlaying(false);
        addEvent(new RoundEndEvent());

        Optional<Player> op;

        if (round.getActivePlayers().size() == 1) {
           op = game.findPlayerByUUID(round.getActivePlayers().get(0).getUUID());
        }
        else {
            Random rand = new Random();

            op = game.findPlayerByUUID(round.getActivePlayers().get(rand.nextInt(round.getActivePlayers().size()-1)).getUUID());
        }

        if (!op.isPresent())
            throw new NoSuchElementException("Can't find winning player");

        Player winningPlayer = op.get();
        winningPlayer.setBank(winningPlayer.getBank() + round.getPot());
        round.setPot(0);
        addEvent(new PlayerWonEvent(winningPlayer));

        logger.writeToFile();
        game.setButton(game.getButton() + 1);
        game.setRoundsCompleted(game.getRoundsCompleted() + 1);

        roundStart();
    }

    private void resetDeck() {
        deck.clear();

        for (Card.Suit suit : Card.Suit.values()) {
            for (int face = 2; face <= 14; face++) {
                deck.push(new Card(face, suit));
            }
        }

        Collections.shuffle(deck);
    }
}
