package org.gruppe2.game.calculation;

import org.gruppe2.game.Card;
import org.gruppe2.game.Hand;

import java.util.Collection;

class Pair implements HandCalculation {

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
        if(cards.size() == 7 &&  amountOfSameFace < 2)
            return false;
        return true;
    }

    @Override
    public double probability(Collection<Card> cards) {
        return 0;
    }

    @Override
    public Hand getType() {
        return Hand.PAIR;
    }

    @Override
    public int compare(Collection<Card> cards, Collection<Card> t1) {
        return 0;
    }
}
