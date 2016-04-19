package org.gruppe2.game.controller;

import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.event.RoundStartEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.model.RoundModel;
import org.gruppe2.game.model.RoundPlayerModel;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Message;
import org.gruppe2.game.session.Model;

import java.util.List;

public class RoundController extends AbstractController {
    @Model
    private RoundModel round;

    @Helper
    private GameHelper game;

    @Override
    public void update() {
        if (round.isPlaying()) {
            // Go to next player and do shit
            addEvent(new PlayerActionQuery(game.findPlayerByUUID(round.getCurrentUUID()).get()));
        }
    }

    @Message
    public boolean roundStart() {
        if (!round.isPlaying()) {
            addEvent(new RoundStartEvent());
            resetRound();
            round.setPlaying(true);
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
}
