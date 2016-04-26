package org.gruppe2.game.calculation;

import org.gruppe2.game.Card;
import org.gruppe2.game.Hand;

import java.util.Collection;

class TwoPairs implements HandCalculation {

    @Override
    public boolean isHand(Collection<Card> cards) {
        return false;
    }

    @Override
    public Collection<Card> getBestCards(Collection<Card> cards) {
        return null;
    }

    @Override
    public boolean canGet(Collection<Card> cards) {
        if (cards == null || cards.size() == 2)
            return true;

        int amountOfSameFace = Generic.amountOfSameFace(cards);

        if(cards.size() <=5)
            return true;
        if(cards.size() == 6 && amountOfSameFace >=2)
            return true;
        if(cards.size() == 7)
            if(Generic.recurringFaceValues(cards).size() >=4)
                return true;


        return false;
    }

    @Override
    public double probability(Collection<Card> cards) {
        return 0;
    }

    @Override
    public Hand getType() {
        return Hand.TWOPAIRS;
    }

    @Override
    public int compare(Collection<Card> cards, Collection<Card> t1) {
        return 0;
    }
}
