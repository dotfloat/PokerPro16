package org.gruppe2.game.calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;

import java.util.Collection;

/**
 * Created by Mikal on 12.04.2016.
 * This class is for evaluations concerning straight flushes
 */
public class StraightFlush implements HandCalculation{

    public static boolean canGetStraightFlush(Collection<Card> communityCards, Player p){
        return Straight.canGetStraight(communityCards, p, true);
    }

    @Override
    public boolean canGetHand(Collection<Card> communityCards, Player p) {
        return canGetStraightFlush(communityCards, p);
    }

    @Override
    public double handProbability(Collection<Card> communityCards, Player p) {
        return 0;
    }

    @Override
    public HandType getType() {
        return HandType.STRAIGHTFLUSH;
    }
}
