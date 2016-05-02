package org.gruppe2.game.calculation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gruppe2.game.Card;
import org.gruppe2.game.Card.Suit;
import org.gruppe2.game.Hand;

class StraightFlush implements HandCalculation{

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

    /**
	 * Assumes both o1 and o2 are StraightFlush.
	 * @return int (1, 0, -1).
	 */
    @Override
    public int compare(List<Card> o1, List<Card> o2) {
    	return Integer.compare(Generic.calculateFacevalueOfAllCards(o1), Generic.calculateFacevalueOfAllCards(o2));
    }

	@Override
	public List<Card> getBestHandCards(List<Card> cards) {
		List<Card> listOfCardsInStraightFlush = new ArrayList<>();
		ArrayList<Card> listOfCardsWithHighestSuit = new ArrayList<>();
		Straight straight = new Straight();
		int diamonds = 0, hearts = 0, spades = 0, clubs = 0;

		// Count all suits:
		for (Card c : cards) {
			switch (c.getSuit()) {
			case DIAMONDS:
				diamonds++;
				break;
			case HEARTS:
				hearts++;
				break;
			case SPADES:
				spades++;
				break;
			case CLUBS:
				clubs++;
				break;
			}
		}

		// Check if any of the suits will result in a flush:
		if (diamonds >= 5)
			listOfCardsWithHighestSuit = cardsOfOneSuit(cards, Suit.DIAMONDS);
		else if (hearts >= 5)
			listOfCardsWithHighestSuit = cardsOfOneSuit(cards, Suit.HEARTS);
		else if (spades >= 5)
			listOfCardsWithHighestSuit = cardsOfOneSuit(cards, Suit.SPADES);
		else if (clubs >= 5)
			listOfCardsWithHighestSuit = cardsOfOneSuit(cards, Suit.CLUBS);

		listOfCardsInStraightFlush = straight.getBestHandCards(listOfCardsWithHighestSuit);

		return listOfCardsInStraightFlush;
	}
	
	private ArrayList<Card> cardsOfOneSuit(List<Card> cards, Suit suit) {
		ArrayList<Card> flushWithSuit = new ArrayList<>();
		Collections.sort(cards);

		// Reversed loop, starts with highest value card.
		for (int i = cards.size() - 1; i >= 0; i--) {
			if (flushWithSuit.size() == 5)
				break;

			if (cards.get(i).getSuit() == suit)
				flushWithSuit.add(cards.get(i));
		}

		// Ignore this, it's never supposed to run, unless previous code has
		// bugs!
		if (flushWithSuit.size() < 5)
			flushWithSuit.clear();

		return flushWithSuit;
	}
}
