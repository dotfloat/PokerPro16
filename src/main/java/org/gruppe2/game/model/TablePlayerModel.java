package org.gruppe2.game.model;

import java.util.UUID;

public class TablePlayerModel {
    private final UUID uuid;
    private final int position;

    private volatile int bet = 0;

    public TablePlayerModel(UUID uuid, int position) {
        this.uuid = uuid;
        this.position = position;
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getPosition() {
        return position;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }
}
