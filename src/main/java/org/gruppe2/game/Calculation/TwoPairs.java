package org.gruppe2.game.Calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;

import java.util.ArrayList;

/**
 * Created by Åsmund on 12/04/2016.
 */
public class TwoPairs {

    public static boolean canGetTwoPairs(ArrayList<Card> communityCards, Player p){
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
}
