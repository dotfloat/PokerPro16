package org.gruppe2.game.event;

import org.gruppe2.game.Action;
import org.gruppe2.game.Player;

public class PlayerPostActionEvent implements Event {
    private final Player player;
    private final Action action;

    public PlayerPostActionEvent(Player player, Action action) {
        this.player = player;
        this.action = action;
    }

    public Player getPlayer() {
        return player;
    }

    public Action getAction() {
        return action;
    }
}
