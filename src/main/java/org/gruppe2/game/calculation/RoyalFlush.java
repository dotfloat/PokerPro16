package org.gruppe2.game.calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Mikal on 11.04.2016.
 */
public class RoyalFlush implements HandCalculation {

    public static boolean canGetRoyalFlush(Collection<Card> communityCards, Player p){
        if (communityCards == null || communityCards.size() == 0)
            return true;

        if (getHighestAmountOfRoyalCardsInSameSuit(communityCards, p) >= communityCards.size())
            return true;

        return false;
    }

    private static boolean cardIsRoyal(Card c) {
        return c.getFaceValue() >= 10 && c.getFaceValue() <= 14;
    }

    private static void royalCardFilter(ArrayList<Card> cards){
        for (Card c : cards)
            if (!cardIsRoyal(c))
                cards.remove(c);
    }

    private static int getHighestAmountOfRoyalCardsInSameSuit(Collection<Card> communityCards, Player p){
        ArrayList<Card> allCards = GeneralCalculations.makeOneListOfCards(communityCards, p);
        royalCardFilter(allCards);

        int highest = 0;

        HashMap<Card.Suit, Integer> numTypes = GeneralCalculations.numberOfEachSuit(allCards);

        for (int i : numTypes.values())
            if (i >= communityCards.size() && i > highest)
                highest = i;

        return highest;
    }

    public double royalFlushProbability(Collection<Card> communityCards, Player p) {
        if (communityCards.size() == 5) {
            if (canGetRoyalFlush(communityCards, p))
                return 1;
            else return 0;
        }

        int amount = getHighestAmountOfRoyalCardsInSameSuit(communityCards, p);
        int cardsLeft = 52 - (communityCards.size() + 2);

        return 0;
    }

    @Override
    public boolean canGetHand(Collection<Card> communityCards, Player p) {
        return canGetRoyalFlush(communityCards, p);
    }

    @Override
    public double handProbability(Collection<Card> communityCards, Player p) {
        return royalFlushProbability(communityCards, p);
    }

    @Override
    public HandType getType() {
        return HandType.ROYALFLUSH;
    }
}
