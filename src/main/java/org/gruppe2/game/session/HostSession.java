package org.gruppe2.game.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.gruppe2.game.controller.AIController;
import org.gruppe2.game.controller.ChatController;
import org.gruppe2.game.controller.GameController;
import org.gruppe2.game.controller.PlayerController;
import org.gruppe2.game.controller.RoundController;
import org.gruppe2.game.model.ChatModel;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.RoundModel;

public class HostSession extends Session {
    private final List<PlayerController> players = Collections.synchronizedList(new ArrayList<>());

    public HostSession(Integer minPlayers, Integer maxPlayers, Integer buyIn, GameModel.BotPolicy botPolicy) {
        addModel(new GameModel(UUID.randomUUID(), minPlayers, maxPlayers, buyIn, botPolicy));
        addModel(new RoundModel());
        addModel(new ChatModel());
    }

    @Override
    public void init() {
        addController(GameController.class);
        addController(RoundController.class);
        addController(AIController.class);
        addController(ChatController.class);
    }

    @Override
    public void update() {

    }
}