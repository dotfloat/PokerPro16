package org.gruppe2.game.Calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Mikal on 11.04.2016.
 */
public class RoyalFlush {

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

        HashMap<Card.Suit, Integer> numTypes = Flush.numberOfEachType(allRoyalCards);

        for (int i : numTypes.values())
            if (i >= communityCards.size())
                return true;

        return false;
    }

    private static boolean cardIsRoyal(Card c) {
        return c.getFaceValue() >= 10 && c.getFaceValue() <= 14;
    }

    public Double royalFlushPossiblity(ArrayList<Card> communityCards, Player p) {
        if (!canGetRoyalFlush(communityCards, p))
            return 0.0;

        return 0.0;
    }
}
