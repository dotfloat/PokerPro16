package org.gruppe2.game.controller;

import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.Model;
import org.gruppe2.game.model.PlayerModel;

public class GameController extends AbstractController<GameModel> {
    @Override
    public void init() {

    }

    @Override
    public void update() {
        getSession().getMessages("addPlayer").forEach(this::addPlayer);
    }

    @Override
    public Class<? extends Model> getModelClass() {
        return GameModel.class;
    }

    private void addPlayer(Object obj) {
        if (!(obj instanceof PlayerModel))
            return;

        PlayerModel model = (PlayerModel) obj;

        System.out.println("Greetings from GameController on " + Thread.currentThread().getName());

        // Todo synchronize the entire model
        if (getModel().getPlayers().size() + 1 < getModel().getMaxPlayers()) {
            getModel().getPlayers().add(model);
            addEvent(PlayerJoinEvent.class, new PlayerJoinEvent(model));
        }
    }
}
