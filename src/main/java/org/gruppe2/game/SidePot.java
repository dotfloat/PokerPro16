package org.gruppe2.game;

import java.util.List;
import java.util.UUID;

public class SidePot {
    private final List<UUID> players;
    private final int pot;

    public SidePot(List<UUID> players, int pot) {
        this.players = players;
        this.pot = pot;
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public int getPot() {
        return pot;
    }

    @Override
    public String toString() {
        return String.format("Pot: %d, players: ", pot) + players;
    }
}
