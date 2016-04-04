package org.gruppe2.game.event;

import org.gruppe2.game.old.Player;

public class PlayerJoinEvent implements Event {
    private Player player;

    public PlayerJoinEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
