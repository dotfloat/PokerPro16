package org.gruppe2.game.event;

import org.gruppe2.game.model.PlayerModel;

public class PlayerJoinEvent implements Event {
    private final PlayerModel playerModel;

    public PlayerJoinEvent(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }
}
