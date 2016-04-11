package org.gruppe2.game.controller;

import org.gruppe2.game.old.Action;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.view.PlayerView;
import org.gruppe2.game.session.SessionContext;

public abstract class AbstractPlayerController extends AbstractController<PlayerModel, PlayerView> {

    public AbstractPlayerController(SessionContext sessionContext) {
        super(sessionContext);
    }

    public abstract Action pollAction();
}
