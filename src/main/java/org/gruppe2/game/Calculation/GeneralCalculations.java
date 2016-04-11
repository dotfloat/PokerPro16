package org.gruppe2.game.Calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Åsmund on 11/04/2016.
 */
public class GeneralCalculations {

    public static int amountOfSameFace(ArrayList<Card> communityCards, Player p){
        HashMap<Integer, Integer> amountCards = new HashMap<Integer, Integer>();
        int amountOfSameKind = 1;

        ArrayList<Card> allCards = new ArrayList<Card>(communityCards);


        allCards.add(p.getCard1());
        allCards.add(p.getCard2());


        for(int i = 0; i < allCards.size(); i++){
            int faceValue = allCards.get(i).getFaceValue();
            if(amountCards.containsKey(faceValue)){
                int amountOfCard = amountCards.get(faceValue) +1;
                amountCards.put(faceValue, amountOfCard);
                if(amountOfSameKind < amountOfCard)
                    amountOfSameKind = amountOfCard;
            }

            else
                amountCards.put(faceValue, 1);
        }
        return amountOfSameKind;
    }
}
