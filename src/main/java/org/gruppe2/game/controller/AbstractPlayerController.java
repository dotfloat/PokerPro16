package org.gruppe2.game.controller;

import org.gruppe2.game.old.Action;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.session.SessionContext;

public abstract class AbstractPlayerController extends AbstractController<PlayerModel> {
    private PlayerModel model = null;

    public AbstractPlayerController(SessionContext sessionContext, PlayerModel model) {
        super(sessionContext, model);
    }

    public PlayerModel getModel() {
        return model;
    }

    public void setModel(PlayerModel model) {
        this.model = model;
    }

    public abstract Action pollAction();
}
