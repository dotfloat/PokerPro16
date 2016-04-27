package org.gruppe2.game;

import org.gruppe2.game.controller.PlayerController;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.session.HostSession;
import org.gruppe2.game.session.Session;
import org.gruppe2.game.session.SessionContext;

public class GameBuilder {
    private int min = 2;
    private int max = 10;
    private int buyIn = 100;
    private Class<PlayerController> playerController = null;
    private GameModel.BotPolicy botPolicy = GameModel.BotPolicy.FILL;

    public GameBuilder playerRange(int min, int max) {
        this.min = min;
        this.max = max;

        return this;
    }

    public GameBuilder buyIn(int buyIn) {
        this.buyIn = buyIn;

        return this;
    }

    public GameBuilder playerController(Class<PlayerController> playerController) {
        this.playerController = playerController;

        return this;
    }

    public SessionContext start() {
        return Session.start(HostSession.class, min, max, buyIn, botPolicy);
    }
    public SessionContext networkStart(){
    	return Session.startServerGame(HostSession.class, min, max, buyIn, botPolicy);
    }
}
