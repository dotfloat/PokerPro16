package org.gruppe2.game.calculation;

import org.gruppe2.game.Card;
import org.gruppe2.game.Hand;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

class Straight implements HandCalculation{

    @Override
    public boolean isHand(List<Card> cards) {
        return false;
    }

    @Override
    public List<Card> getBestCards(List<Card> cards) {
        return null;
    }

    @Override
    public boolean canGet(List<Card> cards) {
        if (cards == null || cards.size() == 2)
            return true;

        return canGetStraight(cards, false);
    }

    public static boolean canGetStraight(List<Card> cards, boolean sameSuit){
        ArrayList<Card> cardArray = new ArrayList<Card>();
        cardArray.addAll(cards);

        for (int i = 0; i < cards.size(); i++){
            int length = checkWithOtherCards(cardArray, i,cardArray.get(i).getFaceValue(), false);

            if (length >= cards.size()-2)
                return true;
            else if (cardArray.get(i).getFaceValue() == 14){
                length = checkWithOtherCards(cardArray, i, 1, false);
                if (length >= cards.size()-2)
                    return true;
            }
        }

        return false;
    }

    private static int checkWithOtherCards(ArrayList<Card> allCards, int i, int faceValue, boolean sameSuit){
        ArrayList<Integer> straight = new ArrayList<>();
        straight.add(faceValue);
        int high = faceValue + 4;
        int low = faceValue - 4;

        for (int j = 0; j < allCards.size(); j++){
            int v2 = allCards.get(j).getFaceValue();

            if (faceValue == v2 || straight.contains(v2))
                continue;
            if (sameSuit && allCards.get(i).getSuit() != allCards.get(j).getSuit())
                continue;

            if (v2 < faceValue && v2 >= low){
                straight.add(v2);
                high -= (faceValue - v2);
            }
            if (v2 > faceValue && v2 <= high) {
                straight.add(v2);
                low += (v2 - faceValue);
            }

            if (v2 == 14){
                v2 = 1;

                if (v2 < faceValue && v2 >= low){
                    straight.add(v2);
                    high -= (faceValue - v2);
                }
                if (v2 > faceValue && v2 <= high) {
                    straight.add(v2);
                    low += (v2 - faceValue);
                }
            }
        }

        return straight.size();
    }

    @Override
    public double probability(List<Card> cards) {
        return 0;
    }

    @Override
    public Hand getType() {
        return Hand.STRAIGHT;
    }

    @Override
    public int compare(List<Card> cards, List<Card> t1) {
        return 0;
    }
}
