package org.gruppe2.game.controller;

import org.gruppe2.ai.AI;
import org.gruppe2.ai.NewDumbAI;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;

/**
 * Created by Mikal on 21.04.2016.
 */
public class AIController extends AbstractController {
    private AI ai = new NewDumbAI();

    @Helper
    private RoundHelper roundHelper;

    @Handler
    public void onAction (PlayerActionQuery query) {
        ai.doAction(query.getPlayer(), roundHelper.getPlayerOptions(query.getPlayer().getUUID()));
    }
}
