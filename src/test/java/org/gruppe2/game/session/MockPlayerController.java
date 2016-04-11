package org.gruppe2.game.session;

import org.gruppe2.game.controller.AbstractPlayerController;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.old.Action;

public class MockPlayerController extends AbstractPlayerController {
    public MockPlayerController(PlayerModel model) {
        super(model);
    }

    @Override
    public Action pollAction() {
        return null;
    }
}
