package org.gruppe2.game.calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class is for evaluations concerning Two Pairs
 */
public class TwoPairs implements HandCalculation{

    public static boolean canGetTwoPairs(Collection<Card> communityCards, Player p){
        if (communityCards == null || communityCards.size() == 0)
            return true;
        
        int amountOfSameFace = GeneralCalculations.amountOfSameFace(communityCards,p);

        if(communityCards.size() <=3)
            return true;
        if(communityCards.size() == 4 && amountOfSameFace >=2)
            return true;
        if(communityCards.size() == 5)
            if(GeneralCalculations.recurringFaceValues(communityCards, p).size() >=4)
                return true;


        return false;
    }

    @Override
    public boolean canGetHand(Collection<Card> communityCards, Player p) {
        return canGetTwoPairs(communityCards,p);
    }

    @Override
    public double handProbability(Collection<Card> communityCards, Player p) {
        return 0;
    }

    @Override
    public HandType getType() {
        return HandType.TWOPAIRS;
    }
}
