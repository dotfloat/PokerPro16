package org.gruppe2.ai;

import static org.junit.Assert.*;

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
	public final static int N = 1000;

	@Test
	public void aiShouldNeverFoldWhenHeCanCheck() {
		AdvancedAI ai = new AdvancedAI();
		Random r = new Random();
		PossibleActions actions = new PossibleActions();
		GameInfo gameHelper = new GameInfo();
		List<RoundPlayer> activePlayers = new ArrayList<>();
		List<Card> communityCards = new ArrayList<>();

		actions.setCheck();

		gameHelper.setActivePlayers(activePlayers);
		gameHelper.setBigBlind(10);
		gameHelper.setCommunityCards(communityCards);
		gameHelper.setHighestBet(0);
		gameHelper.setPossibleActions(actions);

		for (int i = 0; i < 4; i++)
			activePlayers.add(new RoundPlayer(null, new Card(r.nextInt(13) + 2,
					Suit.CLUBS), new Card(r.nextInt(13) + 2, Suit.SPADES)));

		for (int i = 0; i < N; i++) {
			assertFalse(ai.chooseAction(r.nextDouble(), actions, 100, 0,
					gameHelper) instanceof Fold);
		}
	}
}