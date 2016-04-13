package org.gruppe2.game.helper;

import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.session.SessionContext;

import java.util.List;
import java.util.UUID;

public class PlayerHelper {
    private final List<PlayerModel> playerModels;

    public PlayerHelper(SessionContext context) {
        playerModels = context.getModels(PlayerModel.class);
    }

    public boolean containsPlayerByUUID(UUID uuid) {
        return getPlayerByUUID(uuid) != null;
    }

    public PlayerModel getPlayerByUUID(UUID uuid) {
        for (PlayerModel model : playerModels)
            if (model.getUUID().equals(uuid))
                return model;

        return null;
    }

    public PlayerModel getPlayerByName(String name) {
        for (PlayerModel model : playerModels)
            if (model.getName().equals(name))
                return model;

        return null;
    }
}
