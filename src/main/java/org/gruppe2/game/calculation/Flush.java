package org.gruppe2.game.calculation;

import java.util.HashMap;
import java.util.List;

import org.gruppe2.game.Card;
import org.gruppe2.game.Hand;

class Flush implements HandCalculation {

    @Override
    public boolean isHand(List<Card> cards) {
        return false;
    }

    @Override
    public List<Card> getBestCards(List<Card> cards) {
        return null;
    }

    @Override
    public boolean canGet(List<Card> cards) {
        if (cards == null || cards.size() == 2)
            return true;

        HashMap<Card.Suit, Integer> numTypes = Generic.numberOfEachSuit(cards);

        for (Card.Suit suit : numTypes.keySet())
            if (numTypes.get(suit) >= cards.size() -2)
                return true;

        return false;
    }

    @Override
    public double probability(List<Card> cards) {
        return 0;
    }

    @Override
    public Hand getType() {
        return Hand.FLUSH;
    }

    /**
     * Assumes o1 and o2 are already sorted and only includes the 5 Flush cards!
     */
    @Override
    public int compare(List<Card> o1, List<Card> o2) {
    	HighCard highCard = new HighCard();
    	
    	return highCard.compare(o1, o2);
    }

	@Override
	public List<Card> getBestHandCards(List<Card> cards) {
		// TODO Auto-generated method stub
		return null;
	}
}
