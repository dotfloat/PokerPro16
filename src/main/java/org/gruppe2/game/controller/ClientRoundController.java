package org.gruppe2.game.controller;

import org.gruppe2.game.Player;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.event.CommunityCardsEvent;
import org.gruppe2.game.event.PlayerPostActionEvent;
import org.gruppe2.game.event.PlayerWonEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;

import java.util.Optional;

public class ClientRoundController {

    @Helper
    private RoundHelper round;

    @Helper
    private GameHelper game;

    @Handler
    public void onPostAction(PlayerPostActionEvent event) {
        round.setCurrent((round.getCurrent() + 1) % round.getActivePlayers().size());

        Optional<Player> op = game.findPlayerByUUID(round.getCurrentUUID());
        Optional<RoundPlayer> opr = round.findPlayerByUUID(round.getCurrentUUID());

        if (!op.isPresent() || !opr.isPresent())
            return;

        op.get().setBank(event.getPlayer().getBank());
        opr.get().setBet(event.getRoundPlayer().getBet());
    }

    @Handler
    public void onCommunityCards(CommunityCardsEvent event) {
        round.getCommunityCards().clear();
        round.getCommunityCards().addAll(event.getCards());
    }

    @Handler
    public void onPlayerWon(PlayerWonEvent event) {

    }
}
