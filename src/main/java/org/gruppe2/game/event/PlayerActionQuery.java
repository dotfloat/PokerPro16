package org.gruppe2.game.event;

import org.gruppe2.game.Player;
import org.gruppe2.game.RoundPlayer;

public class PlayerActionQuery implements Event {
    private final Player player;
    private final RoundPlayer roundPlayer;

    public PlayerActionQuery(Player player, RoundPlayer roundPlayer) {
        this.player = player;
        this.roundPlayer = roundPlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public RoundPlayer getRoundPlayer() {
        return roundPlayer;
    }
}
