package org.gruppe2.game.event;

import org.gruppe2.game.model.PlayerModel;

public class PlayerPreActionEvent implements Event {
    private final PlayerModel playerModel;

    public PlayerPreActionEvent(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }
}
