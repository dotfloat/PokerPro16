package org.gruppe2.game.controller;

import org.gruppe2.ai.AI;
import org.gruppe2.ai.NewDumbAI;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;

import java.time.LocalDateTime;

public class AIController extends AbstractController {
    private AI ai = new NewDumbAI();

    @Helper
    private RoundHelper roundHelper;

    @Handler
    public void onAction(PlayerActionQuery query) {
        if (!query.getPlayer().isBot())
            return;

        setTask(5, () -> {
            ai.doAction(query.getPlayer(), roundHelper.getPlayerOptions(query.getPlayer().getUUID()));
        });
    }
}
