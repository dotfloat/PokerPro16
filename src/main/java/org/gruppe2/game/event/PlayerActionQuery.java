package org.gruppe2.game.event;

import org.gruppe2.game.Action;
import org.gruppe2.game.session.Query;
import org.gruppe2.game.model.PlayerModel;

public class PlayerActionQuery implements Event {
    private final PlayerModel playerModel;

    public PlayerActionQuery(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }
}
