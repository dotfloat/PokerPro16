package org.gruppe2.backend;

import org.gruppe2.ai.AIBuilder;

public class GameBuilder {
    private int numAI = 0;
    private GameClient client = null;
    private int bigBlind = 0;
    private int smallBlind = 0;
    private int startMoney = 0;

    public GameBuilder ai(int numAI) {
        this.numAI = numAI;

        return this;
    }

    public GameBuilder mainClient(GameClient client) {
        this.client = client;

        return this;
    }

    public GameBuilder blinds(int bigBlind, int smallBlind) {
        this.bigBlind = bigBlind;
        this.smallBlind = smallBlind;

        return this;
    }

    public GameBuilder startMoney(int startMoney) {
        this.startMoney = startMoney;

        return this;
    }

    public GameSession build() {
        GameSession session = new GameSession(smallBlind, bigBlind);

        if (client != null) {
            session.addPlayer(client);
        }

        for (int i = 0; i <  numAI; i++) {
            session.addPlayer(new AIBuilder().build());
        }

        return session;
    }
}
