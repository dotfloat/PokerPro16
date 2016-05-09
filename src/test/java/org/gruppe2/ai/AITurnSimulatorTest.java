package org.gruppe2.ai;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.gruppe2.game.Card;
import org.gruppe2.game.Card.Suit;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Session;
import org.gruppe2.game.session.SessionContext;
import org.junit.Test;

public class AITurnSimulatorTest {

	@Test
	public void test() {
		AITurnSimulator aits = new AITurnSimulator();
		RoundPlayer rp = new RoundPlayer(null,new Card(14,Suit.CLUBS),new Card(14,Suit.DIAMONDS));
		ArrayList<Card> communityCards = new ArrayList<Card>();
		communityCards.add(new Card(14,Suit.HEARTS));
		communityCards.add(new Card(14,Suit.SPADES));
		communityCards.add(new Card(7,Suit.DIAMONDS));
		double winChance = aits.getHandStregth(rp, communityCards, 1000, 4);
		System.out.println(winChance);
	}
}
