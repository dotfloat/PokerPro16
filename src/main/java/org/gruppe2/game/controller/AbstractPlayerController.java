package org.gruppe2.game.controller;

import org.gruppe2.game.old.Action;
import org.gruppe2.game.model.PlayerModel;

public abstract class AbstractPlayerController extends AbstractController {
    private PlayerModel model = null;

    public PlayerModel getModel() {
        return model;
    }

    public void setModel(PlayerModel model) {
        this.model = model;
    }

    public abstract Action pollAction();
}
