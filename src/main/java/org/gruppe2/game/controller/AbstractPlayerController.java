package org.gruppe2.game.controller;

import org.gruppe2.game.old.Action;
import org.gruppe2.game.model.PlayerModel;

public abstract class AbstractPlayerController extends AbstractController<PlayerModel> {
    private PlayerModel model = null;

    public AbstractPlayerController(PlayerModel model) {
        super(model);
    }

    public PlayerModel getModel() {
        return model;
    }

    public void setModel(PlayerModel model) {
        this.model = model;
    }

    public abstract Action pollAction();
}
