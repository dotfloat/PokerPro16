package org.gruppe2.game.controller;

import org.gruppe2.ai.AI;
import org.gruppe2.ai.NewDumbAI;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.session.Handler;

/**
 * Created by Mikal on 21.04.2016.
 */
public class AIController extends AbstractController {
    private AI dumbAI = new NewDumbAI();

    @Handler
    public void onAction (PlayerActionQuery query) {
        dumbAI.doAction(query.getPlayerModel());
    }
}
