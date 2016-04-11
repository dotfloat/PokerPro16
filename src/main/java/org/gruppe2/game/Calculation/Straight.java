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

        int straightLength = 0;

        ArrayList<Card> allCards = new ArrayList<>(communityCards);
        allCards.add(p.getCard1());
        allCards.add(p.getCard2());

        for (int i = 0; i < allCards.size(); i++){
            straightLength++;
            for (int j = i+1; j < allCards.size(); j++){
                int v1 = allCards.get(i).getFaceValue();
                int v2 = allCards.get(j).getFaceValue();

                if (v1 == v2)
                    continue;
                if (v2 < v1 && v2 >= v1 - 4)
                    straightLength++;
                if (v2 > v1 && v1 >= v2 - 4)
                    straightLength++;
            }
            if (straightLength >= communityCards.size())
                return true;
            else
                straightLength = 0;
        }

        return false;
    }
}
