package org.gruppe2.game.Calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Mikal on 11.04.2016.
 */
public class Straight {

    public boolean canGetStraight(Collection<Card> communityCards, Player p){
        if (communityCards.size() == 0)
            return true;

        ArrayList<Card> allCards = new ArrayList<>(communityCards);
        allCards.add(p.getCard1());
        allCards.add(p.getCard2());

        for (int i = 0; i < allCards.size(); i++){
            int length = checkWithOtherCards(allCards, i, allCards.get(i).getFaceValue());

            if (length >= communityCards.size())
                return true;
            else if (allCards.get(i).getFaceValue() == 14){
                length = checkWithOtherCards(allCards, i, 1);
                if (length >= communityCards.size())
                    return true;
            }
        }

        return false;
    }

    private int checkWithOtherCards(ArrayList<Card> allCards, int i, int faceValue){
        int straightLength = 1;
        int v1 = faceValue;
        int high = v1 + 4;
        int low = v1 - 4;

        for (int j = i+1; j < allCards.size(); j++){
            int v2 = allCards.get(j).getFaceValue();

            if (v1 == v2)
                continue;
            if (v2 < v1 && v2 >= low){
                straightLength++;
                high -= (v1 - v2);
            }
            if (v2 > v1 && v2 <= high) {
                straightLength++;
                low += (v2 - v1);
            }

            if (v2 == 14){
                v2 = 1;

                if (v2 < v1 && v2 >= low){
                    straightLength++;
                    high -= (v1 - v2);
                }
                if (v2 > v1 && v2 <= high) {
                    straightLength++;
                    low += (v2 - v1);
                }
            }
        }

        return straightLength;
    }
}
