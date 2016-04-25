package org.gruppe2.game.model;

import org.gruppe2.game.old.PossibleActions;

import java.util.UUID;

public class RoundPlayerModel {
    private final UUID uuid;

    private volatile int bet = 0;
    private volatile PossibleActions options = null;

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

    public PossibleActions getOptions() {
        return options;
    }

    public void setOptions(PossibleActions options) {
        this.options = options;
    }
}
