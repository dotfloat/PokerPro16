package org.gruppe2.game.controller;

import org.gruppe2.ai.NewDumbAI;
import org.gruppe2.game.Action;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Message;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.session.Query;

import java.util.UUID;

public class GameController extends AbstractController {
    @Helper
    private GameHelper game;

    @Message
    public void addBot() {
        addPlayerModel(NewDumbAI.generateModel());
    }

    @Message
    public void addPlayer(UUID uuid, String name, String avatar, Query<Action> action) {
        addPlayerModel(new PlayerModel(uuid, name, avatar, action, false));
    }

    @Message
    public void kickPlayer(UUID uuid) {
    }

    private void addPlayerModel(PlayerModel model) {
    }
}
