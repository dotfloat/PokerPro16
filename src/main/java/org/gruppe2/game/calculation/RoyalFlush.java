package org.gruppe2.game.calculation;

import org.gruppe2.game.Card;
import org.gruppe2.game.Hand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

class RoyalFlush implements HandCalculation {
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

        if (getHighestAmountOfRoyalCardsInSameSuit(cards) >= cards.size() -2)
            return true;

        return false;
    }

    private static int getHighestAmountOfRoyalCardsInSameSuit(Collection<Card> cards){
        Collection<Card> allCards = royalCardFilter(cards);

        int highest = 0;

        HashMap<Card.Suit, Integer> numTypes = Generic.numberOfEachSuit(allCards);

        for (int i : numTypes.values())
            if (i >= cards.size() && i > highest)
                highest = i;

        return highest;
    }

    private static Collection<Card> royalCardFilter(Collection<Card> cards){
        Collection<Card> newList = new ArrayList<>();
        for (Card c : cards)
            if (cardIsRoyal(c))
                newList.add(c);
        return newList;
    }

    private static boolean cardIsRoyal(Card c) {
        return c.getFaceValue() >= 10 && c.getFaceValue() <= 14;
    }

    @Override
    public double probability(Collection<Card> cards) {
        return 0;
    }

    @Override
    public Hand getType() {
        return Hand.ROYALFLUSH;
    }

    @Override
    public int compare(Collection<Card> cards, Collection<Card> t1) {
        return 0;
    }
}
