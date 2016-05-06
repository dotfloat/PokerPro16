package org.gruppe2.game.event;

import org.gruppe2.game.Player;

public class PlayerPreActionEvent implements Event {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6697919760037783069L;
	private final Player player;

    public PlayerPreActionEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
