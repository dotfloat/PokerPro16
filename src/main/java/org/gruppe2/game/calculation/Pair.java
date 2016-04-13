package org.gruppe2.game.calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class is for evaluations concerning Pairs
 */
public class Pair implements HandCalculation{
    public static Boolean canGetPair(Collection<Card> communityCards, Player p){
        if (communityCards == null || communityCards.size() == 0)
            return true;

        int amountOfSameFace = GeneralCalculations.amountOfSameFace(communityCards, p);
        if(communityCards.size() == 5 &&  amountOfSameFace < 2)
            return false;
        return true;
    }

    @Override
    public boolean canGetHand(Collection<Card> communityCards, Player p) {
        return canGetPair(communityCards,p);
    }

    @Override
    public double handProbability(Collection<Card> communityCards, Player p) {
        return 0;
    }

    @Override
    public HandType getType() {
        return HandType.PAIR;
    }
}
