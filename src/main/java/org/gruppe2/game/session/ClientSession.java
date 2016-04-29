package org.gruppe2.game.session;

import org.gruppe2.game.controller.NetworkClientController;
import org.gruppe2.game.model.ChatModel;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.RoundModel;
import org.gruppe2.game.model.StatisticsModel;

import java.util.UUID;

public class ClientSession extends Session {

    public ClientSession(String ip) {
        addModel(new GameModel(UUID.randomUUID(), 0, 10, 1000, GameModel.BotPolicy.FILL, 10, 20));
        addModel(new RoundModel());
        addModel(new ChatModel());
        addModel(new StatisticsModel());
    }

    @Override
    public void init() {
		addController(NetworkClientController.class);
        sendMessage("connect");
    }

    @Override
    public void update() {
    	
    }

}
