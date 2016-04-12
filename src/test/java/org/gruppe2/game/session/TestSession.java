package org.gruppe2.game.session;

import org.gruppe2.game.GameState;
import org.gruppe2.game.controller.PlayerController;
import org.gruppe2.game.event.EventHandler;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.model.PlayerModel;

public abstract class TestSession extends Session {
    @Override
    public void update() {

    }

    @Override
    public void exit() {

    }

    //@Override
    public boolean addPlayer(PlayerModel model, EventHandler<PlayerActionQuery> handler) {
        return false;
    }
}
