package org.gruppe2.game.calculation;

import org.gruppe2.game.Card;
import org.gruppe2.game.Hand;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

class FullHouse implements HandCalculation {

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

        int amountSameFace = Generic.amountOfSameFace(cards);
        ArrayList<Integer> recurringCards = Generic.recurringFaceValues(cards);
        if(cards.size() <5)
            return true;
        if(cards.size() == 5 && amountSameFace >=2)
            return true;
        if(cards.size() == 6 && recurringCards.size() >=3)
            return true;
        if(cards.size() == 7 && recurringCards.size() >=5)
            return true;

        return false;
    }

    @Override
    public double probability(List<Card> cards) {
        return 0;
    }

    @Override
    public Hand getType() {
        return Hand.FULLHOUSE;
    }

    @Override
    public int compare(List<Card> cards, List<Card> t1) {
        return 0;
    }
}
