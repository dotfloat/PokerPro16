package org.gruppe2.ai;

/**
 * Builder class pattern for AIClient
 *
 * Used to more easily create AIClients
 */
public class AIBuilder {
    private String name = null;
    private Difficulty difficulty = Difficulty.RANDOM;

    /**
     * @param name AI's name or null to generate a random one
     * @return itself
     */
    public AIBuilder name(String name) {
        this.name = name;

        return this;
    }

    /**
     * AI difficulty. Defaults to random.
     * @param difficulty difficulty
     * @return itself
     */
    public AIBuilder difficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    /**
     * Create a new AIClient object and apply settings from the builder
     * @return new AIClient object
     */
    public AIClient build() {
        AIClient ai = new AIClient();

        if (name != null) {
            ai.setName(name);
        }

        ai.setDifficulty(difficulty);

        return ai;
    }
}
