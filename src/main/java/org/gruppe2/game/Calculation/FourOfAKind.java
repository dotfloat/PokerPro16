package org.gruppe2.game.Calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;

import java.util.ArrayList;

/**
 * Created by Åsmund on 11/04/2016.
 */
public class FourOfAKind {


    public static boolean canGetFourOfAKind(ArrayList<Card> communityCards, Player p){

        int amountOfSameFace = GeneralCalculations.amountOfSameFace(communityCards, p);

        if(communityCards.size() < 3)
            return true;
        if(communityCards.size() == 3 &&  amountOfSameFace >=2)
            return true;
        if(communityCards.size() == 4 && amountOfSameFace >=3)
            return true;
        if (communityCards.size() == 5 && amountOfSameFace == 4)
            return true;

        return false;
    }
}
