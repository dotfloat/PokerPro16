package org.gruppe2.game.calculation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.List;

import org.gruppe2.game.Card;
import org.gruppe2.game.Hand;

public class HighCard implements HandCalculation {

	@Override
	public boolean isHand(List<Card> cards) {
		if(cards == null || cards.isEmpty())
			return false;
		
		return true;
	}

	@Override
	public List<Card> getBestCards(List<Card> cards) {
		ArrayList<Card> highestCard = new ArrayList<>();
		
		Collections.sort(cards);
		highestCard.add(cards.get(cards.size()-1));
		
		return highestCard;
	}

	@Override
	public boolean canGet(List<Card> cards) {
		return true;
	}

	@Override
	public double probability(List<Card> cards) {
		return 1.0;
	}

	@Override
	public Hand getType() {
		return Hand.HIGHCARD;
	}

	@Override
	public int compare(List<Card> o1, List<Card> o2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
