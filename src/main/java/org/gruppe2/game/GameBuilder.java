package org.gruppe2.game;

import org.gruppe2.game.controller.PlayerController;
import org.gruppe2.game.session.GameSession;
import org.gruppe2.game.session.SessionContext;

public class GameBuilder {
    private int maxPlayers = 10;
    private Class<PlayerController> playerController = null;

    public GameBuilder maxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;

        return this;
    }

    public GameBuilder playerController(Class<PlayerController> playerController) {
        this.playerController = playerController;

        return this;
    }

    public SessionContext start() {
        return new GameSession(maxPlayers).start();
    }
}
