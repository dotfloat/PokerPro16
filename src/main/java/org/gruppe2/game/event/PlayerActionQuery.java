package org.gruppe2.game.event;

import org.gruppe2.game.Action;
import org.gruppe2.game.session.Query;
import org.gruppe2.game.model.PlayerModel;

public class PlayerActionQuery implements Event {
    private final PlayerModel playerModel;
    private final Query<Action> query;

    public PlayerActionQuery(PlayerModel playerModel, Query<Action> query) {
        this.playerModel = playerModel;
        this.query = query;
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    public Query<Action> getQuery() {
        return query;
    }
}
