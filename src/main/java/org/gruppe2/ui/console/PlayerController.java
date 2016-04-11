package org.gruppe2.ui.console;

import org.gruppe2.game.controller.AbstractPlayerController;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.old.Action;
import org.gruppe2.game.session.SessionContext;

public class PlayerController extends AbstractPlayerController {

    public PlayerController(SessionContext sessionContext) {
        super(sessionContext);
    }

    @Override
    public Action pollAction() {
        return null;
    }
}
