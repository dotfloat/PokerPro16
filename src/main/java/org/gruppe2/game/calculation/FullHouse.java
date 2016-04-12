package org.gruppe2.game.calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Åsmund on 12/04/2016.
 */
public class FullHouse implements HandCalculation{

    public static boolean canGetFullHouse(Collection<Card> communityCards, Player p){
        int amountSameFace = GeneralCalculations.amountOfSameFace(communityCards,p);
        ArrayList<Integer> recurringCards = GeneralCalculations.recurringFaceValues(communityCards, p);
        if(communityCards.size() <3)
            return true;
        if(communityCards.size() == 3 && amountSameFace >=2)
            return true;
        if(communityCards.size() == 4 && recurringCards.size() >=3)
            return true;
        if(communityCards.size() == 5 && recurringCards.size() >=5)
            return true;

        return false;
    }

    @Override
    public boolean canGetHand(Collection<Card> communityCards, Player p) {
        return canGetFullHouse(communityCards, p);
    }

    @Override
    public double handProbability(Collection<Card> communityCards, Player p) {
        return 0;
    }
}
