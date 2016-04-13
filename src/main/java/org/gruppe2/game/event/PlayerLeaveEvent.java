package org.gruppe2.game.event;

import org.gruppe2.game.model.PlayerModel;

public class PlayerLeaveEvent implements Event {
    private final PlayerModel playerModel;

    public PlayerLeaveEvent(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }
}
