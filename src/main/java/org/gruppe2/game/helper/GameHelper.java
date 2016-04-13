package org.gruppe2.game.helper;

import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.session.SessionContext;

import java.util.UUID;

public class GameHelper {
    private final GameModel model;
    private final PlayerHelper playerHelper;

    public GameHelper(SessionContext context) {
        model = context.getModel(GameModel.class);

        playerHelper = new PlayerHelper(context);
    }

    public UUID getButtonPlayerUUID() {
        return model.getPlayers().get(model.getButton());
    }

    public PlayerModel getButtonPlayer() {
        return playerHelper.getPlayerByUUID(getButtonPlayerUUID());
    }

    public boolean canStart() {
        return model.getPlayers().size() > model.getMinPlayers();
    }
}
