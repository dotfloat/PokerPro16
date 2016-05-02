package org.gruppe2.game.calculation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gruppe2.game.Card;
import org.gruppe2.game.Hand;

class Straight implements HandCalculation{

    @Override
    public boolean isHand(List<Card> cards) {
    	if(cards == null || getBestHandCards(cards).isEmpty())
    		return false;
    	
    	return true;
    }

    @Override
    public List<Card> getBestCards(List<Card> cards) {
    	return getBestHandCards(cards);
    }

    @Override
    public boolean canGet(List<Card> cards) {
        if (cards == null || cards.size() == 2)
            return true;

        return canGetStraight(cards, false);
    }

    static boolean canGetStraight(List<Card> cards, boolean sameSuit){
        for (int i = 0; i < cards.size(); i++){
            int length = checkWithOtherCards(cards, i,cards.get(i).getFaceValue(), sameSuit);

            if (length >= cards.size()-2)
                return true;
            else if (cards.get(i).getFaceValue() == 14){
                length = checkWithOtherCards(cards, i, 1, sameSuit);
                if (length >= cards.size()-2)
                    return true;
            }
        }

        return false;
    }

    private static int checkWithOtherCards(List<Card> allCards, int i, int faceValue, boolean sameSuit){
        ArrayList<Integer> straight = new ArrayList<>();
        straight.add(faceValue);
        int high = faceValue + 4;
        int low = faceValue - 4;

        for (int j = 0; j < allCards.size(); j++){
            int v2 = allCards.get(j).getFaceValue();

            if (faceValue == v2 || straight.contains(v2))
                continue;
            if (sameSuit && allCards.get(i).getSuit() != allCards.get(j).getSuit())
                continue;

            if (v2 < faceValue && v2 >= low){
                straight.add(v2);
                high -= (faceValue - v2);
            }
            if (v2 > faceValue && v2 <= high) {
                straight.add(v2);
                low += (v2 - faceValue);
            }

            if (v2 == 14){
                v2 = 1;

                if (v2 < faceValue && v2 >= low){
                    straight.add(v2);
                    high -= (faceValue - v2);
                }
                if (v2 > faceValue && v2 <= high) {
                    straight.add(v2);
                    low += (v2 - faceValue);
                }
            }
        }

        return straight.size();
    }

    @Override
    public double probability(List<Card> cards) {
        return 0;
    }

    @Override
    public Hand getType() {
        return Hand.STRAIGHT;
    }

    /**
     * Assumes o1 and o2 are already sorted and only includes the 5 Straight cards!
     */
    @Override
    public int compare(List<Card> o1, List<Card> o2) {
    	HighCard highCard = new HighCard();
    	
    	return highCard.compare(o1, o2);
    }

	@Override
	public List<Card> getBestHandCards(List<Card> cards) {
		ArrayList<Card> listOfCardsInStraight = new ArrayList<>();
		if (cards.size() >= 1) {
			Collections.sort(cards);
			int cardsInARow = 1; // Starter card always counts.

			// Face value of highest card
			int lastCardValue = cards.get(cards.size() - 1).getFaceValue();
			listOfCardsInStraight.add(cards.get(cards.size() - 1));

			// Simple reversed loop, skipping the highest card.
			for (int i = (cards.size() - 2); (i + 1) > 0; i--) {
				if (listOfCardsInStraight.size() == 5)
					break;

				// If the next card is 1 less than the previous, then it counts!
				if ((cards.get(i).getFaceValue() + 1) == lastCardValue) {
					cardsInARow++;
					listOfCardsInStraight.add(cards.get(i));
				} else if ((cards.get(i).getFaceValue() + 1) < lastCardValue) {
					listOfCardsInStraight.clear();
					cardsInARow = 1;
					listOfCardsInStraight.add(cards.get(i));
				}

				lastCardValue = cards.get(i).getFaceValue();
			}

			// Special case for Ace as it can also count as a "1"
			if (lastCardValue == 2 && cardsInARow == 4
					&& cards.get(cards.size() - 1).getFaceValue() == 14)
				listOfCardsInStraight.add(cards.get(cards.size() - 1));

			if (listOfCardsInStraight.size() != 5)
				listOfCardsInStraight.clear();

		}

		return listOfCardsInStraight;

	}
}
