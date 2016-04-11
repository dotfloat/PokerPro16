package org.gruppe2.game.controller;

import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.session.SessionContext;

public class GameController extends AbstractController<GameModel> {
    public GameController(SessionContext sessionContext, GameModel model) {
        super(sessionContext, model);
    }

    public void update() {
    }
}
