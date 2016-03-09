package org.gruppe2.backend;

/**
 * Created by Mikal on 09.03.16.
 */
public class Player {
    private String name;
    private int chips;

    public Player(String name, int startChips) {
        this.name = name;
        this.chips = startChips;
    }

    public int getChips() {
        return chips;
    }

    public String getName(){
        return name;
    }
}
