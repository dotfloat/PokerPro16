package org.gruppe2.game.calculation;

import java.util.List;

import org.gruppe2.game.Card;
import org.gruppe2.game.Hand;

class StraightFlush implements HandCalculation{

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

        return Straight.canGetStraight(cards,true);
    }

    @Override
    public double probability(List<Card> cards) {
        return 0;
    }

    @Override
    public Hand getType() {
        return Hand.STRAIGHTFLUSH;
    }

    @Override
    public int compare(List<Card> cards, List<Card> t1) {
        return 0;
    }

	@Override
	public List<Card> getBestHandCards(List<Card> cards) {
		// TODO Auto-generated method stub
		return null;
	}
}
