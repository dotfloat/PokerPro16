package org.gruppe2.game.calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;
import org.gruppe2.game.old.ShowdownEvaluator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Mikal on 11.04.2016.
 */
public class Flush implements HandCalculation{

    public static boolean canGetFlush(Collection<Card> communityCards, Player p){
        if (communityCards == null || communityCards.size() == 0)
            return true;

        ArrayList<Card> allCards = new ArrayList<>(communityCards);
        allCards.add(p.getCard1());
        allCards.add(p.getCard2());

        HashMap<Card.Suit, Integer> numTypes = GeneralCalculations.numberOfEachType(allCards);

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
