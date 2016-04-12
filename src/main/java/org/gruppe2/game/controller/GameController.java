package org.gruppe2.game.controller;

import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.session.SessionContext;
import org.gruppe2.game.view.GameView;

public class GameController extends Controller<GameModel, GameView> {
    PlayerController playerController = new PlayerController(getSessionContext());
    PlayerModel model = null;

    public GameController(SessionContext sessionContext) {
        super(sessionContext);
    }

    @Override
    public void init() {
    }

    @Override
    public void update() {
        if (model != null) {
            getSessionContext().addEvent(PlayerActionQuery.class, model, new PlayerActionQuery());
            model = null;
        }
    }

    @Override
    public GameModel getModel() {
        return getSessionContext().getGameModel();
    }

    @Override
    public GameView getView() {
        return getSessionContext().getGameView();
    }

    public void addPlayer(PlayerModel model) {
        getModel().getPlayers().add(model);

        this.model = model;

        getSessionContext().addEvent(PlayerJoinEvent.class, new PlayerJoinEvent(model));
    }
}
