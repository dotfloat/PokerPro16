package org.gruppe2.ai;

import org.gruppe2.backend.GameClient;

public class AIFactory {
    private Difficulty difficulty;

    public AIFactory(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public GameClient generateBot() {
        switch (difficulty) {
            case EASY:
            case MEDIUM:
            case HARD:
            case RANDOM:
            default:
                return new AIClient();
        }
    }
}
