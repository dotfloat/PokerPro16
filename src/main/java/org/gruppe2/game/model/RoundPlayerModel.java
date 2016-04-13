package org.gruppe2.game.model;

import java.util.UUID;

public class RoundPlayerModel {
    private final UUID uuid;

    private volatile int bet = 0;

    public RoundPlayerModel(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }
}
