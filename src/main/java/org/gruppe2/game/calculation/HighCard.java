package org.gruppe2.game.calculation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gruppe2.game.Card;
import org.gruppe2.game.Hand;

public class HighCard implements HandCalculation {

	@Override
	public boolean isHand(List<Card> cards) {
		if (cards == null || cards.isEmpty())
			return false;

		return true;
	}

	@Override
	public List<Card> getBestCards(List<Card> cards) {
		ArrayList<Card> highestCard = new ArrayList<>();

		Collections.sort(cards);
		for (int i = 0; i < 5; i++) {
			if ((cards.size() - i) > 0)
				highestCard.add(cards.get(cards.size() - 1 - i));
		}

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

	/**
	 * Assumes both o1 and o2 are already sorted!
	 * @return int (1, 0, -1).
	 */
	@Override
	public int compare(List<Card> o1, List<Card> o2) {
		for (int i = 0; i < o1.size() && i < o2.size(); i++) {
			int comp = o1.get(i).compareTo(o2.get(i));
			if (comp != 0)
				return comp;
		}

		return 0;
	}

	@Override
	public List<Card> getBestHandCards(List<Card> cards) {
		return getBestCards(cards);
	}

}
