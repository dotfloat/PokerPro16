package org.gruppe2.game.calculation;

import org.gruppe2.game.Card;
import org.gruppe2.game.Hand;

import java.util.List;
import java.util.List;

class Pair implements HandCalculation {

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

    @Override
    public int compare(List<Card> cards, List<Card> t1) {
        return 0;
    }
}
