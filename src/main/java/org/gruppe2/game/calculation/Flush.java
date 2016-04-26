package org.gruppe2.game.calculation;

import org.gruppe2.game.Card;
import org.gruppe2.game.Hand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

class Flush implements HandCalculation {

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

        if (cards == null || cards.size() == 0)
            return true;

        HashMap<Card.Suit, Integer> numTypes = Generic.numberOfEachSuit(cards);

        for (Card.Suit suit : numTypes.keySet())
            if (numTypes.get(suit) >= cards.size() -2)
                return true;

        return false;
    }

    @Override
    public double probability(Collection<Card> cards) {
        return 0;
    }

    @Override
    public Hand getType() {
        return Hand.FLUSH;
    }

    @Override
    public int compare(Collection<Card> cards, Collection<Card> t1) {
        return 0;
    }
}
