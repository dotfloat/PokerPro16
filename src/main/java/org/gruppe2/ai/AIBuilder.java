package org.gruppe2.ai;

public class AIBuilder {
    private String name = null;

    public AIBuilder name(String name) {
        this.name = name;

        return this;
    }

    public AIClient build() {
        AIClient ai = new AIClient();

        if (name != null) {
            ai.setName(name);
        }

        return ai;
    }
}
