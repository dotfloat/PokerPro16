package org.gruppe2.game;

import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.session.HostSession;
import org.gruppe2.game.session.Session;
import org.gruppe2.game.session.SessionContext;

public class GameBuilder {
    private int min = 2;
    private int max = 10;
    private int buyIn = 100;
    private int smallBlind = 10;
    private int bigBlind = 20;
    private GameModel.BotPolicy botPolicy = GameModel.BotPolicy.NONE;

    public GameBuilder playerRange(int min, int max) {
        this.min = min;
        this.max = max;

        return this;
    }

    public GameBuilder buyIn(int buyIn) {
        this.buyIn = buyIn;

        return this;
    }

    
    public GameBuilder blinds(int smallBlind, int bigBlind){
        this.smallBlind = smallBlind;
        this.bigBlind = bigBlind;

        return this;
    }

    public GameBuilder botPolicy (GameModel.BotPolicy policy) {
        botPolicy = policy;

        return this;
    }

    public SessionContext start() {
        return Session.start(HostSession.class, min, max, buyIn, botPolicy, smallBlind, bigBlind);
    }
}
