package org.gruppe2.game.controller;

import org.gruppe2.game.Message;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.Model;
import org.gruppe2.game.model.PlayerModel;

public class GameController extends AbstractController<GameModel> {

    @Override
    public Class<? extends Model> getModelClass() {
        return GameModel.class;
    }

    @Message
    public void addPlayer(PlayerModel model) {
        System.out.println("Greetings from GameController on " + Thread.currentThread().getName());

        // Todo synchronize the entire model
        if (getModel().getPlayers().size() + 1 < getModel().getMaxPlayers()) {
            getModel().getPlayers().add(model);
            addEvent(PlayerJoinEvent.class, new PlayerJoinEvent(model));
        }
    }

    @Message
    public void sayInChat(String name, String message) {
        System.out.println(name + ": " + message);
    }
}
