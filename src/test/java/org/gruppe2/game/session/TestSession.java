package org.gruppe2.game.session;

import org.gruppe2.game.GameState;

public class TestSession extends Session {
    private GameState state = GameState.WAITING_FOR_PLAYERS;

    @Override
    GameState getGameState() {
        return state;
    }
}
