package org.gruppe2.game.session;

import org.gruppe2.game.controller.GameController;
import org.gruppe2.game.controller.PlayerController;
import org.gruppe2.game.event.EventHandler;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.Model;
import org.gruppe2.game.model.PlayerModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class GameSession extends Session {
    private final List<PlayerController> players = Collections.synchronizedList(new ArrayList<>());

    private boolean playing = true;

    public GameSession(int maxPlayers) {
        addModels(GameModel.class);
        getModels(GameModel.class).add(new GameModel(UUID.randomUUID(), maxPlayers));

        addModels(PlayerModel.class);

        addController(GameController.class);
    }

    public boolean isPlaying() {
        return playing;
    }

    @Override
    public void exit() {
        playing = false;
    }
}