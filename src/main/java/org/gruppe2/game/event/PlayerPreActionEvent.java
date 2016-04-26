package org.gruppe2.game.event;

import org.gruppe2.game.Player;

public class PlayerPreActionEvent implements Event {
    private final Player player;

    public PlayerPreActionEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
