package org.gruppe2.game.event;

import org.gruppe2.game.model.PlayerModel;

/**
 * Created by Åsmund on 13/04/2016.
 */
public class PlayerWonEvent implements Event{
    private final PlayerModel playerModel;

    public PlayerWonEvent(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }

    public PlayerModel getPlayerModel(){
        return playerModel;
    }


}
