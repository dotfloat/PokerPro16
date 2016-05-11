package org.gruppe2.ai;

public enum Difficulty {
    EASY("Easy"),
    NORMAL("Normal"),
    HARD("Hard");

    private final String name;

    Difficulty(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
