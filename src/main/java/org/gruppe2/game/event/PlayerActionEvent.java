package org.gruppe2.game.event;

import org.gruppe2.game.old.Player;

public class PlayerActionEvent implements Event {
    private final Player player;

    public PlayerActionEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
