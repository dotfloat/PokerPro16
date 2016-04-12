package org.gruppe2.game.calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;

import java.util.ArrayList;

/**
 * Created by �smund on 11/04/2016.
 */
public class Pair {
    public static Boolean canGetPair(ArrayList<Card> communityCards, Player p){
        int amountOfSameFace = GeneralCalculations.amountOfSameFace(communityCards, p);
        if(communityCards.size() == 5 &&  amountOfSameFace < 2)
            return false;
        return true;
    }
}
