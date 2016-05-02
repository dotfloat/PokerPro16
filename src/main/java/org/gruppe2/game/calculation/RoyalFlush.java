package org.gruppe2.game.calculation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.gruppe2.game.Card;
import org.gruppe2.game.Hand;

class RoyalFlush implements HandCalculation {
    @Override
    public boolean isHand(List<Card> cards) {
        return false;
    }

    @Override
    public List<Card> getBestCards(List<Card> cards) {
    	return getBestHandCards(cards);
    }

    @Override
    public boolean canGet(List<Card> cards) {
        if (cards == null || cards.size() == 2)
            return true;

        if (getHighestAmountOfRoyalCardsInSameSuit(cards) >= cards.size() -2)
            return true;

        return false;
    }

    private static int getHighestAmountOfRoyalCardsInSameSuit(List<Card> cards){
        List<Card> allCards = royalCardFilter(cards);

        int highest = 0;

        HashMap<Card.Suit, Integer> numTypes = Generic.numberOfEachSuit(allCards);

        for (int i : numTypes.values())
            if (i >= cards.size() && i > highest)
                highest = i;

        return highest;
    }

    private static List<Card> royalCardFilter(List<Card> cards){
        List<Card> newList = new ArrayList<>();
        for (Card c : cards)
            if (cardIsRoyal(c))
                newList.add(c);
        return newList;
    }

    private static boolean cardIsRoyal(Card c) {
        return c.getFaceValue() >= 10 && c.getFaceValue() <= 14;
    }

    @Override
    public double probability(List<Card> cards) {
        return 0;
    }

    @Override
    public Hand getType() {
        return Hand.ROYALFLUSH;
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
