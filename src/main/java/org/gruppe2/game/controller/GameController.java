package org.gruppe2.game.controller;

import org.gruppe2.game.Message;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.model.PlayerModel;

import java.util.List;

public class GameController extends AbstractController {

    @Message
    public void addPlayer(PlayerModel model) {
        System.out.println("Greetings from GameController on " + Thread.currentThread().getName());
        List<PlayerModel> players;

        synchronized (players = getModels(PlayerModel.class)) {
            players.add(model);
            addEvent(new PlayerJoinEvent(model));
        }
    }
}
