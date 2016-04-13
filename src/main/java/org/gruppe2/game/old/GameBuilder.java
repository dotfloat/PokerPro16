package org.gruppe2.game.old;

import org.gruppe2.ai.AIBuilder;
import org.gruppe2.ai.Difficulty;

import java.util.Random;

/**
 * This is a simple class used to build a new game. It includes som default values.
 * If a client wants to use different values it's allowed to change them using the
 * different methods. When done altering the values, the client building the game should
 * call build().
 */
public class GameBuilder {
    //Default values:
    private int numAI = 4;
    private GameClient client = null;
    private int bigBlind = 20;
    private int smallBlind = 10;
    private int startMoney = 100;
    private GameBuilderAiDifficultyOptions aiDifficulty = GameBuilderAiDifficultyOptions.MIXED;
    private Random random = new Random();

    /**
     * Change number of AIs. numAI can't be bigger than 9 or less than 3.
     * @param numAI number of AIs in game
     * @return this
     */
    public GameBuilder ai(int numAI) {
        if(numAI > 9)
            this.numAI = 9;
        else if(numAI < 3)
            this.numAI = 3;
        else
            this.numAI = numAI;

        return this;
    }

    /**
     * Change AI difficulty
     * @param difficulty
     * @return this
     */
    public GameBuilder aiDifficulty(GameBuilderAiDifficultyOptions difficulty) {
        this.aiDifficulty = difficulty;
        return this;
    }

    /**
     * Set the main client. This must be set as this is the client running the game
     * @param client
     * @return this
     */
    public GameBuilder mainClient(GameClient client) {
        this.client = client;

        return this;
    }

    /**
     * Set blinds
     * @param bigBlind
     * @param smallBlind
     * @return this
     */
    public GameBuilder blinds(int bigBlind, int smallBlind) {
        this.bigBlind = bigBlind;
        this.smallBlind = smallBlind;

        return this;
    }

    /**
     * Set start money
     * @param startMoney
     * @return this
     */
    public GameBuilder startMoney(int startMoney) {
        this.startMoney = startMoney;

        return this;
    }

    /**
     * Build the game
     * @return a new GameSession running the game
     */
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
