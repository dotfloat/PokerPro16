package org.gruppe2.game.controller;

import org.gruppe2.game.Action;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.event.PlayerPostActionEvent;
import org.gruppe2.game.event.RoundStartEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.model.RoundModel;
import org.gruppe2.game.model.RoundPlayerModel;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Message;
import org.gruppe2.game.session.Model;

import java.time.LocalDateTime;
import java.util.List;

public class RoundController extends AbstractController {
    @Model
    private RoundModel round;

    @Helper
    private GameHelper game;

    private LocalDateTime timeToStart = null;
    private PlayerModel player = null;

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
                player = game.findPlayerByUUID(round.getCurrentUUID()).get();
                addEvent(new PlayerActionQuery(player));
            }
            if (player.getAction().isDone()) {
                handleAction(player);
                player.getAction().reset();
                player = null;
            }
            System.out.println(player.getName());
        }
    }

    @Message
    public boolean roundStart() {
        if (!round.isPlaying()) {
            round.setPlaying(true);
            timeToStart = LocalDateTime.now().plusSeconds(5);
            return true;
        }

        return false;
    }

    private void resetRound(){
        List<RoundPlayerModel> active = round.getActivePlayers();
        active.clear();

        for (PlayerModel p: game.getPlayers())
            if (p.getBank() > 0 )
                active.add(new RoundPlayerModel(p.getUUID()));

        round.setPot(0);
        round.setCurrent(game.getButton());
    }

    private void handleAction (PlayerModel player){
        Action action = player.getAction().get();
        if (action instanceof Action.Fold)
            round.getActivePlayers().remove(round.getCurrent());
        if (action instanceof Action.Call)


        addEvent(new PlayerPostActionEvent(player, action));
    }
}
