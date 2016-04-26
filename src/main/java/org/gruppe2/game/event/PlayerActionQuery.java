package org.gruppe2.game.event;

import org.gruppe2.game.Player;

public class PlayerActionQuery implements Event {
    private final Player player;

    public PlayerActionQuery(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
