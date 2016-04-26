package org.gruppe2.game.controller;

import org.gruppe2.game.Action;
import org.gruppe2.game.Player;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.event.PlayerPostActionEvent;
import org.gruppe2.game.event.RoundStartEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class RoundController extends AbstractController {
    @Helper
    private RoundHelper round;
    @Helper
    private GameHelper game;

    private LocalDateTime timeToStart = null;
    private Player player = null;
    private UUID lastRaiserID = null;
    private UUID lastPlayerInRound = null;

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
                addEvent(new PlayerActionQuery(player));
            }
            if (player.getAction().isDone()) {
                handleAction(player);
                player.getAction().reset();
                player = null;
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

        for (Player p: game.getPlayers())
            if (p.getBank() > 0 )
                active.add(new RoundPlayer(p.getUUID(), null, null));

        round.setPot(0);
        round.setHighestBet(0);
        round.setCurrent(game.getButton());
        lastPlayerInRound = round.getCurrentUUID();
    }

    private void handleAction (Player player){
        Action action = player.getAction().get();
        if (!legalAction(player, action))
            throw new IllegalArgumentException(player.getName() + " can't do action: " + action);

        int raise;
        if (action instanceof Action.Call) {
            raise = round.getHighestBet() - player.getBet();
            moveChips(player, player.getBet() + raise, player.getBank() - raise, raise);
        }

        if (action instanceof Action.AllIn) {
            raise = player.getBank();
            moveChips(player, player.getBet() + raise, 0, raise);
        }

        if (action instanceof Action.Blind) {
            int amount = ((Action.Blind) action).getAmount();
            moveChips(player, amount, player.getBank() - amount, amount);
        }

        if (action instanceof Action.Fold) {
            round.getActivePlayers().remove(round.getCurrent());
            round.setCurrent(round.getCurrent()-1);
            if (!player.getUUID().equals(lastPlayerInRound))
                lastPlayerInRound = round.getLastActivePlayerID();
        }

        if (action instanceof Action.Raise) {
            raise = ((Action.Raise) action).getAmount();
            int chipsToMove = (round.getHighestBet() - player.getBet()) + raise;
            moveChips(player, round.getHighestBet() + raise, player.getBank()-chipsToMove, chipsToMove);
            lastRaiserID = player.getUUID();
        }

        if(player.getBet() > round.getHighestBet())
            round.setHighestBet(player.getBet());

        addEvent(new PlayerPostActionEvent(player, action));

        if (!(action instanceof Action.Raise) && ((lastRaiserID == null && player.getUUID().equals(lastPlayerInRound)) || player.getUUID().equals(lastRaiserID))) {
            roundEnd();
        }
    }

    private void moveChips(Player player, int playerSetBet, int playerSetBank, int addToTablePot){
        //player.setBet(playerSetBet);
        player.setBank(playerSetBank);
        round.addToPot(addToTablePot);
    }

    private boolean legalAction(Player player, Action action) {
        return true;
    }

    /*
    private void updatePlayerOptions () {
        PossibleActions options = new PossibleActions();

        if (player.getBet() == round.getHighestBet())
            options.setCheck();

        if (player.getBank() >= round.getHighestBet() - player.getBet())
            if (round.getHighestBet() - player.getBet() != 0)
                options.setCall(round.getHighestBet() - player.getBet());

        if (!player.getUUID().equals(lastRaiserID)) {
            int maxRaise = player.getBank() + player.getBet() - round.getHighestBet();
            if (maxRaise > 0)
                options.setRaise(1, maxRaise);
        }

        if (!options.canCall() && !options.canCheck() && !options.canRaise())
            options.setAllIn();

        player.setOptions(options);
    }
    */

    private void roundEnd() {
        //endRound
    }
}
