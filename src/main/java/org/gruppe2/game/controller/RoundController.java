package org.gruppe2.game.controller;

import org.gruppe2.game.model.RoundModel;
import org.gruppe2.game.session.SessionContext;
import org.gruppe2.game.view.RoundView;

public class RoundController extends Controller<RoundModel, RoundView> {
    public RoundController(SessionContext sessionContext) {
        super(sessionContext);
    }

    @Override
    public void update() {
        
    }
}
