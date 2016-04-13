package org.gruppe2.ai;

public class AIBuilder {
    private String name = null;
    private Difficulty difficulty = Difficulty.RANDOM;

    public AIBuilder name(String name) {
        this.name = name;

        return this;
    }

    public AIBuilder difficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public AIClient build() {
        AIClient ai = new AIClient();

        if (name != null) {
            ai.setName(name);
        }

        ai.setDifficulty(difficulty);

        return ai;
    }
}
