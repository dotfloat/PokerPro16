package org.gruppe2.ai;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.gruppe2.game.Action.Fold;
import org.gruppe2.game.Card;
import org.gruppe2.game.Card.Suit;
import org.gruppe2.game.PossibleActions;
import org.gruppe2.game.RoundPlayer;
import org.junit.Test;

public class AdvancedAITest {
	public final static int N = 10000;

	@Test
	public void aiShouldNeverFoldWhenHeCanCheck() {
		AdvancedAI ai = new AdvancedAI();
		Random r = new Random();
		PossibleActions actions = new PossibleActions();
		GameInfo gameHelper = new GameInfo();
		List<RoundPlayer> activePlayers = new ArrayList<>();
		List<Card> communityCards = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			actions.setCheck();

			gameHelper.setActivePlayers(activePlayers);
			gameHelper.setBigBlind(r.nextInt(1000));
			gameHelper.setCommunityCards(communityCards);
			gameHelper.setHighestBet(r.nextInt(1000));
			gameHelper.setPossibleActions(actions);

			for (int j = 0; j < 4; j++)
				activePlayers.add(new RoundPlayer(null, new Card(r.nextInt(13) + 2,
						Suit.CLUBS), new Card(r.nextInt(13) + 2, Suit.SPADES)));

			assertFalse(ai.chooseAction(r.nextDouble()*r.nextInt(100), 0, 0, actions, r.nextInt(100), r.nextDouble(),
					gameHelper) instanceof Fold);
		}
	}
}