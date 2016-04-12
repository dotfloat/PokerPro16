package org.gruppe2.game.calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Mikal on 11.04.2016.
 */
public class Straight implements HandCalculation{

    public static boolean canGetStraight(Collection<Card> communityCards, Player p, boolean sameSuit){
        if (communityCards.size() == 0)
            return true;

        ArrayList<Card> allCards = new ArrayList<>(communityCards);
        allCards.add(p.getCard1());
        allCards.add(p.getCard2());

        for (int i = 0; i < allCards.size(); i++){
            int length = checkWithOtherCards(allCards, i, allCards.get(i).getFaceValue(), sameSuit);

            if (length >= communityCards.size())
                return true;
            else if (allCards.get(i).getFaceValue() == 14){
                length = checkWithOtherCards(allCards, i, 1, sameSuit);
                if (length >= communityCards.size())
                    return true;
            }
        }

        return false;
    }

    private static int checkWithOtherCards(ArrayList<Card> allCards, int i, int faceValue, boolean sameSuit){
        int straightLength = 1;
        int high = faceValue + 4;
        int low = faceValue - 4;

        for (int j = i+1; j < allCards.size(); j++){
            int v2 = allCards.get(j).getFaceValue();

            if (faceValue == v2)
                continue;
            if (sameSuit && allCards.get(i).getSuit() != allCards.get(j).getSuit())
                continue;
            if (v2 < faceValue && v2 >= low){
                straightLength++;
                high -= (faceValue - v2);
            }
            if (v2 > faceValue && v2 <= high) {
                straightLength++;
                low += (v2 - faceValue);
            }

            if (v2 == 14){
                v2 = 1;

                if (v2 < faceValue && v2 >= low){
                    straightLength++;
                    high -= (faceValue - v2);
                }
                if (v2 > faceValue && v2 <= high) {
                    straightLength++;
                    low += (v2 - faceValue);
                }
            }
        }

        return straightLength;
    }

    @Override
    public boolean canGetHand(Collection<Card> communityCards, Player p) {
        return canGetStraight(communityCards, p, false);
    }

    @Override
    public double handProbability(Collection<Card> communityCards, Player p) {
        return 0.0;
    }
}