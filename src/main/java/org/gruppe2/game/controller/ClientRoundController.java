package org.gruppe2.game.controller;

import org.gruppe2.game.Player;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.event.*;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;

import java.util.Optional;

public class ClientRoundController extends AbstractController{

    @Helper
    private RoundHelper round;

    @Helper
    private GameHelper game;

    @Handler
    public void onPlayerAction(PlayerActionQuery event) {
        updatePlayer(event.getPlayer(), event.getRoundPlayer());
    }

    @Handler
    public void onPostAction(PlayerPostActionEvent event) {
        round.setCurrent((round.getCurrent() + 1) % round.getActivePlayers().size());

        updatePlayer(event.getPlayer(), event.getRoundPlayer());
    }

    @Handler
    public void onCommunityCards(CommunityCardsEvent event) {
        round.getCommunityCards().clear();
        round.getCommunityCards().addAll(event.getCards());
    }

    @Handler
    public void onPlayerWin(PlayerWonEvent event) {

    }

    private void updatePlayer(Player p, RoundPlayer rp) {
        Optional<Player> op = game.findPlayerByUUID(p.getUUID());
        Optional<RoundPlayer> opr = round.findPlayerByUUID(p.getUUID());

        if (!op.isPresent() || !opr.isPresent())
            return;

        op.get().setBank(p.getBank());
        opr.get().setBet(rp.getBet());
    }
}
