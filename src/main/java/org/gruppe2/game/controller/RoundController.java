package org.gruppe2.game.controller;

import org.gruppe2.game.Message;
import org.gruppe2.game.event.RoundStartEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.model.RoundModel;

public class RoundController extends AbstractController {
    private GameHelper gameHelper;

    @Override
    public void init() {
        super.init();

        gameHelper = new GameHelper(getContext());
    }

    @Override
    public void update() {
        super.update();

        if (!getModel().isPlaying() && gameHelper.canStart()) {
            start();
        }
    }

    private void start() {
        getModel().setPlaying(true);

        addEvent(new RoundStartEvent());
    }

    private RoundModel getModel() {
        return getModel(RoundModel.class);
    }
}
