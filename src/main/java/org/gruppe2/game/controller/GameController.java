package org.gruppe2.game.controller;

import org.gruppe2.ai.NewDumbAI;
import org.gruppe2.game.Helper;
import org.gruppe2.game.Message;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.event.PlayerLeaveEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.PlayerModel;

import java.util.List;
import java.util.UUID;

public class GameController extends AbstractController {
    @Helper
    private GameHelper gameHelper;

    @Override
    public void update() {
        super.update();

        if (checkForPlayers())
            for (int i = getModel().getPlayers().size(); i < getModel().getMaxPlayers(); i++) {
                addBot();
            }
    }

    private boolean checkForPlayers() {
        return false;
    }

    @Message
    public void addBot() {
        addPlayerModel(NewDumbAI.generateModel());
    }

    @Message
    public void addPlayer(UUID uuid, String name, String avatar) {
        addPlayerModel(new PlayerModel(uuid, name, avatar, false));
    }

    @Message
    public void kickPlayer(UUID uuid) {
    }

    private GameModel getModel() {
        return getModel(GameModel.class);
    }

    private void addPlayerModel(PlayerModel model) {
    }
}
