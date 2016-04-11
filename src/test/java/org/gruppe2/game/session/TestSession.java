package org.gruppe2.game.session;

import org.gruppe2.game.GameState;
import org.gruppe2.game.controller.AbstractPlayerController;

public class TestSession extends Session {
    private GameState state = GameState.WAITING_FOR_PLAYERS;

    @Override
    GameState getGameState() {
        return state;
    }

    @Override
    public boolean addPlayer(AbstractPlayerController controller) {
        return false;
    }

    @Override
    public void exit() {

    }

    @Override
    public int getMaxPlayers() {
        return 0;
    }

    @Override
    public void update() {

    }
}
