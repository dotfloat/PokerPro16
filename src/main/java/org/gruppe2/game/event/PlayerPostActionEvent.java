package org.gruppe2.game.event;

import org.gruppe2.game.Action;
import org.gruppe2.game.model.PlayerModel;

public class PlayerPostActionEvent implements Event {
    private final PlayerModel playerModel;
    private final Action action;

    public PlayerPostActionEvent(PlayerModel playerModel, Action action) {
        this.playerModel = playerModel;
        this.action = action;
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    public Action getAction() {
        return action;
    }
}
