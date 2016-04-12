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
        if (communityCards.size() == 0)
            return true;

        ArrayList<Card> allRoyalCards = new ArrayList<>();
        for (Card c : communityCards)
            if (cardIsRoyal(c))
                allRoyalCards.add(c);
        if (cardIsRoyal(p.getCard1()))
            allRoyalCards.add(p.getCard1());
        if (cardIsRoyal(p.getCard2()))
            allRoyalCards.add(p.getCard2());

        if (allRoyalCards.size() < communityCards.size())
            return false;

        HashMap<Card.Suit, Integer> numTypes = GeneralCalculations.numberOfEachType(allRoyalCards);

        for (int i : numTypes.values())
            if (i >= communityCards.size())
                return true;

        return false;
    }

    private static boolean cardIsRoyal(Card c) {
        return c.getFaceValue() >= 10 && c.getFaceValue() <= 14;
    }

    public double royalFlushProbability(Collection<Card> communityCards, Player p) {
        if (!canGetRoyalFlush(communityCards, p))
            return 0;

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
