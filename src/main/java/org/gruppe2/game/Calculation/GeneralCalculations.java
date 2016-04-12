package org.gruppe2.game.Calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by �smund on 11/04/2016.
 */
public class GeneralCalculations {

    public static ArrayList<Integer> recurringFaceValues(Collection<Card> communityCards, Player p) {
        ArrayList<Integer> recurringFaceValues = new ArrayList<Integer>();

        ArrayList<Card> allCards = new ArrayList<Card>(communityCards);

        HashMap<Integer, Integer> hashMapCards = new HashMap<Integer, Integer>();

        if (p.getCard1() != null && p.getCard2() != null) {
            allCards.add(p.getCard1());
            allCards.add(p.getCard2());

        }

        for (int i = 0; i < allCards.size(); i++) {
            int faceValue = allCards.get(i).getFaceValue();
            if (hashMapCards.containsKey(faceValue))
                hashMapCards.put(faceValue, hashMapCards.get(faceValue) + 1);

            else hashMapCards.put(faceValue, 1);

        }
        for(int i= 2; i < Card.ACE +1; i++){
            if(hashMapCards.containsKey(i)){
                if(hashMapCards.get(i) > 1)
                    for(int j = 0; j < hashMapCards.get(i); j++){
                        recurringFaceValues.add(i);
                    }
            }
        }
        return recurringFaceValues;
    }



    public static int amountOfSameFace(Collection<Card> communityCards, Player p){
        HashMap<Integer, Integer> amountCards = new HashMap<Integer, Integer>();
        int amountOfSameKind = 1;

        ArrayList<Card> allCards = new ArrayList<Card>(communityCards);

        if(p.getCard1() != null && p.getCard2() != null) {
            allCards.add(p.getCard1());
            allCards.add(p.getCard2());

        }


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
