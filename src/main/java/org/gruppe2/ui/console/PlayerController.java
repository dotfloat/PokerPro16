package org.gruppe2.ui.console;

import org.gruppe2.game.controller.AbstractPlayerController;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.old.Action;

public class PlayerController extends AbstractPlayerController {

    public PlayerController(PlayerModel model) {
        super(model);
    }

    @Override
    public Action pollAction() {
        return null;
    }
}
