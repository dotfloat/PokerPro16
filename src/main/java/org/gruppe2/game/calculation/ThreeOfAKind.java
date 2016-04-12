package org.gruppe2.game.calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;
import org.gruppe2.ui.javafx.CommunityCards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by �smund on 11/04/2016.
 */
public class ThreeOfAKind implements HandCalculation{

    public static boolean canGetThreeOfAKind(Collection<Card> communityCards, Player p){
        int amountOfSameFace = GeneralCalculations.amountOfSameFace(communityCards,p);

        if(amountOfSameFace >= 3)
            return true;
        if (communityCards.size() <= 3)
            return true;
        if(communityCards.size() == 4){
            if(p.getCard1() == p.getCard2())
                return true;
            if(amountOfSameFace == 2)
                return true;
        }

        return false;
    }

    @Override
    public boolean canGetHand(Collection<Card> communityCards, Player p) {
        return canGetThreeOfAKind(communityCards, p);
    }

    @Override
    public double handProbability(Collection<Card> communityCards, Player p) {
        return 0;
    }
}
