package org.gruppe2.game;

import org.gruppe2.game.session.GameSession;
import org.gruppe2.game.session.SessionContext;

public class GameBuilder {
    private int maxPlayers = 10;

    public GameBuilder maxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;

        return this;
    }

    public SessionContext start() {
        return new GameSession(maxPlayers).start();
    }
}
