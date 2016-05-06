package org.gruppe2.game.event;

import org.gruppe2.game.Player;

/**
 * Created by Åsmund on 13/04/2016.
 */
public class PlayerWonEvent implements Event{
    /**
	 * 
	 */
	private static final long serialVersionUID = -372752616850376543L;
	private final Player player;

    public PlayerWonEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer(){
        return player;
    }


}
