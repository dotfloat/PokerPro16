package org.gruppe2.game.controller;

import org.gruppe2.Main;
import org.gruppe2.ai.AI;
import org.gruppe2.ai.AdvancedAI;
import org.gruppe2.ai.GameInfo;
import org.gruppe2.ai.NewDumbAI;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;

public class AIController extends AbstractController {
	private AI ai = new AdvancedAI();

	@Helper
	private GameHelper gameHelper;

	@Helper
	private RoundHelper roundHelper;


	@Handler
	public void onAction(PlayerActionQuery query) {
		//Use dumb AI for difficulty easy
		if(Main.getProperty("botDiff").equals("Easy")) {
			ai = new NewDumbAI();
		}

		if (!query.getPlayer().isBot())
			return;

        //Create GameInfo POJO and fill with the info AI needs
        GameInfo gameInfo = new GameInfo();
        gameInfo.setBigBlind(gameHelper.getBigBlind());
        gameInfo.setCommunityCards(roundHelper.getCommunityCards());
        gameInfo.setPossibleActions(roundHelper.getPlayerOptions(query.getPlayer().getUUID()));
        gameInfo.setActivePlayers(roundHelper.getActivePlayers());
        gameInfo.setHighestBet(roundHelper.getHighestBet());

		setTask(gameHelper.getWaitTime(), () -> {
			ai.doAction(query.getPlayer(),
                    query.getRoundPlayer(),
					gameInfo);
		});
	}
}
