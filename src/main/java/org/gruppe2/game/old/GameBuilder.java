package org.gruppe2.game.old;

import org.gruppe2.ai.AIBuilder;
import org.gruppe2.ai.Difficulty;

import java.util.Random;

public class GameBuilder {
    private int numAI = 0;
    private GameClient client = null;
    private int bigBlind = 0;
    private int smallBlind = 0;
    private int startMoney = 0;
    private GameBuilderAiDifficultyOptions aiDifficulty = GameBuilderAiDifficultyOptions.MIXED;
    private Random random = new Random();

    public GameBuilder ai(int numAI) {
        this.numAI = numAI;

        return this;
    }

    public GameBuilder aiDifficulty(GameBuilderAiDifficultyOptions difficulty) {
        this.aiDifficulty = difficulty;
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
            session.addPlayer(client, startMoney);
        }

        //Set difficulty for AI. There is a nicer way to do this, but deadline is coming.
        //Will probably clean up later
        for (int i = 0; i < numAI; i++) {
            Difficulty difficulty;
            switch (aiDifficulty) {
                case MIXED:
                    difficulty = Difficulty.values()[random.nextInt(Difficulty.values().length)];
                    break;
                case ADVANCED:
                    difficulty = Difficulty.ADVANCED;
                    break;
                case RANDOM:
                    difficulty = Difficulty.RANDOM;
                    break;
                default:
                    difficulty = Difficulty.ADVANCED;
                    break;
            }

            session.addPlayer(new AIBuilder().difficulty(difficulty).build(), startMoney);
        }

        return session;
    }
}
