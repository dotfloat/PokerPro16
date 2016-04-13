package org.gruppe2.game.calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Mikal on 11.04.2016.
 * This class is for evaluations concerning Flush
 */
public class Flush implements HandCalculation{

    public static boolean canGetFlush(Collection<Card> communityCards, Player p){
        if (communityCards == null || communityCards.size() == 0)
            return true;

        ArrayList<Card> allCards = GeneralCalculations.makeOneListOfCards(communityCards, p);

        HashMap<Card.Suit, Integer> numTypes = GeneralCalculations.numberOfEachSuit(allCards);

        for (Card.Suit suit : numTypes.keySet())
            if (numTypes.get(suit) >= communityCards.size())
                return true;

        return false;
    }

    @Override
    public boolean canGetHand(Collection<Card> communityCards, Player p) {
        return canGetFlush(communityCards, p);
    }

    @Override
    public double handProbability(Collection<Card> communityCards, Player p) {
        return 0;
    }

    @Override
    public HandType getType() {
        return HandType.FLUSH;
    }
}
