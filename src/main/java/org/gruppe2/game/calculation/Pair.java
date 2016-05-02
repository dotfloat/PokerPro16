package org.gruppe2.game.calculation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.gruppe2.game.Card;
import org.gruppe2.game.Hand;

class Pair implements HandCalculation {

    @Override
    public boolean isHand(List<Card> cards) {
        return false;
    }

    @Override
    public List<Card> getBestCards(List<Card> cards) {
        ArrayList<Card> getBestCards = new ArrayList<>();
        HighCard highCard = new HighCard();
        List<Card> purePairCards = getBestHandCards(cards);
        
        cards.removeAll(purePairCards);
        
        List<Card> highCards = highCard.getBestCards(cards);
        
        getBestCards.addAll(purePairCards);
        for(int i = highCards.size()-1; i >= 0; i--) {
        	getBestCards.add(highCards.get(i));
        	if(getBestCards.size() >= 5)
        		break;
        }
        
        return getBestCards;
    }

    @Override
    public boolean canGet(List<Card> cards) {
        if (cards == null || cards.size() == 2)
            return true;

        int amountOfSameFace = Generic.amountOfSameFace(cards);
        if(cards.size() == 7 &&  amountOfSameFace < 2)
            return false;
        return true;
    }

    @Override
    public double probability(List<Card> cards) {
        return 0;
    }

    @Override
    public Hand getType() {
        return Hand.PAIR;
    }

    /**
	 * Assumes both o1 and o2 are pairs excluding Highcards.
	 * @return int (1, 0, -1).
	 */
    @Override
    public int compare(List<Card> o1, List<Card> o2) {
        return Integer.compare(Generic.calculateFacevalueOfAllCards(o1), Generic.calculateFacevalueOfAllCards(o2));
    }

	@Override
	public List<Card> getBestHandCards(List<Card> cards) {
		ArrayList<Card> listOfCardsInPair = new ArrayList<>();

		HashMap<Integer, Integer> recurringFaceValues = Generic.recurringFaceValuesMap(cards);
		int highestCardValue = -1;
		
		for(int i= Card.ACE; i >= 2; i--){
            if(recurringFaceValues.containsKey(i)){
                if(recurringFaceValues.get(i) > 1) {
                	highestCardValue = i;
                	break;
                }
                    
            }
        }
		
		if(highestCardValue > 1) {
			for(Card c : cards) {
				if(c.getFaceValue() == highestCardValue) {
					listOfCardsInPair.add(c);
					if(listOfCardsInPair.size() >= 2)
						break;
				}
			}
		}

		return listOfCardsInPair;
	}
}
