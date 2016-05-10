package org.gruppe2.game.session;

import java.util.UUID;

import org.gruppe2.game.controller.*;
import org.gruppe2.game.model.ChatModel;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.RoundModel;
import org.gruppe2.game.model.StatisticsModel;

public class HostSession extends Session {
    

    public HostSession(Integer minPlayers, Integer maxPlayers, Integer buyIn, GameModel.BotPolicy botPolicy, Integer smallBlind, Integer bigBlind, Integer waitTime) {
        addModel(new GameModel(UUID.randomUUID(), minPlayers, maxPlayers, buyIn, botPolicy, smallBlind, bigBlind, waitTime));
        addModel(new RoundModel());
        addModel(new ChatModel());
        addModel(new StatisticsModel());
    }

    @Override
    public void init() {
        addController(GameController.class);
        addController(RoundController.class);
        addController(AIController.class);
        addController(ChatController.class);
        addController(StatisticsController.class);
        addController(NetworkServerController.class);
        addController(RecordController.class);
    }

    @Override
    public void update() {

    }
}