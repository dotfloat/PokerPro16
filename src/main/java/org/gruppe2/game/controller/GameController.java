package org.gruppe2.game.controller;

import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.session.SessionContext;
import org.gruppe2.game.view.GameView;

public class GameController extends AbstractController<GameModel, GameView> {
    public GameController(SessionContext sessionContext) {
        super(sessionContext);
    }

    public void update() {
    }
}
