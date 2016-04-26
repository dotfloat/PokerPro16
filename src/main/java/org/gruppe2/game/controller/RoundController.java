package org.gruppe2.game.controller;

import org.gruppe2.game.*;
import org.gruppe2.game.event.*;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RoundController extends AbstractController {
    @Helper
    private RoundHelper round;
    @Helper
    private GameHelper game;

    private LocalDateTime timeToStart = null;
    private Player player = null;
    private RoundPlayer roundPlayer = null;
    private UUID lastPlayerInRound = null;
    private List<Card> deck = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void update() {
        if (round.isPlaying() && timeToStart != null) {
            if (LocalDateTime.now().isAfter(timeToStart)) {
                timeToStart = null;

                addEvent(new RoundStartEvent());
                resetRound();
            } else {
                return;
            }
        }

        if (round.isPlaying()) {
            // Go to next player and do shit
            if (player == null) {
                round.setCurrent((round.getCurrent() + 1) % round.getActivePlayers().size());
                player = game.findPlayerByUUID(round.getCurrentUUID());
                roundPlayer = round.findPlayerByUUID(round.getCurrentUUID());
                addEvent(new PlayerPreActionEvent(player));
                addEvent(new PlayerActionQuery(player, roundPlayer));
            }
            if (player.getAction().isDone()) {
                handleAction();
                player.getAction().reset();
                player = null;
                roundPlayer = null;
            }
        }
    }

    @Message
    public boolean roundStart() {
        if (!round.isPlaying()) {
            round.setPlaying(true);
            timeToStart = LocalDateTime.now().plusSeconds(3);
            return true;
        }

        return false;
    }

    private void resetRound(){
        List<RoundPlayer> active = round.getActivePlayers();
        active.clear();
        resetDeck();

        for (Player p: game.getPlayers())
            if (p.getBank() > 0 )
                active.add(new RoundPlayer(p.getUUID(), deck.remove(0), deck.remove(0)));

        round.setPot(0);
        round.setHighestBet(0);
        round.setCurrent(game.getButton());
        lastPlayerInRound = round.getCurrentUUID();
    }

    private void handleAction (){
        Action action = player.getAction().get();
        if (!legalAction(player, action))
            throw new IllegalArgumentException(player.getName() + " can't do action: " + action);

        int raise;
        if (action instanceof Action.Call) {
            raise = round.getHighestBet() - roundPlayer.getBet();
            moveChips(roundPlayer.getBet() + raise, player.getBank() - raise, raise);
        }

        if (action instanceof Action.AllIn) {
            raise = player.getBank();
            moveChips(roundPlayer.getBet() + raise, 0, raise);
        }

        if (action instanceof Action.Blind) {
            int amount = ((Action.Blind) action).getAmount();
            moveChips(amount, player.getBank() - amount, amount);
        }

        if (action instanceof Action.Fold) {
            round.getActivePlayers().remove(round.getCurrent());
            round.setCurrent(round.getCurrent()-1);
            if (!player.getUUID().equals(lastPlayerInRound))
                lastPlayerInRound = round.getLastActivePlayerID();
        }

        if (action instanceof Action.Raise) {
            raise = ((Action.Raise) action).getAmount();
            int chipsToMove = (round.getHighestBet() - roundPlayer.getBet()) + raise;
            moveChips(round.getHighestBet() + raise, player.getBank()-chipsToMove, chipsToMove);
            round.setLastRaiserID(player.getUUID());
        }

        if(roundPlayer.getBet() > round.getHighestBet())
            round.setHighestBet(roundPlayer.getBet());

        addEvent(new PlayerPostActionEvent(player, action));

        if (!(action instanceof Action.Raise)
                && ((round.getLastRaiserID() == null && player.getUUID().equals(lastPlayerInRound))
                || player.getUUID().equals(round.getLastRaiserID()))) {
            roundEnd();
        }
    }

    private void moveChips(int playerSetBet, int playerSetBank, int addToTablePot){
        roundPlayer.setBet(playerSetBet);
        player.setBank(playerSetBank);
        round.addToPot(addToTablePot);
    }

    private boolean legalAction(Player player, Action action) {
        return true;
    }

    private void roundEnd() {
        if (round.getRoundNum() == 3){
            round.setPlaying(false);
            addEvent(new RoundEndEvent());
            return;
        }
        round.nextRound();
        round.setLastRaiserID(null);

        if (round.getRoundNum() == 1) {
            for (int i = 0; i < 3; i++)
                round.getCommunityCards().add(deck.remove(0));
        }
        else if (round.getRoundNum() == 2 || round.getRoundNum() == 3)
            round.getCommunityCards().add(deck.remove(0));

        addEvent(new CommunityCardsEvent(round.getCommunityCards()));
    }

    private void resetDeck() {
        deck.clear();
        for (Card.Suit suit : Card.Suit.values()) {
            for (int face = 2; face <= 14; face++) {
                deck.add(new Card(face, suit));
            }
        }

        Collections.shuffle(deck);
    }
}
