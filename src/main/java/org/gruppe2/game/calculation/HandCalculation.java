package org.gruppe2.game.calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;

import java.util.Collection;

/**
 * Created by Mikal on 12.04.2016.
 */
public interface HandCalculation {

    /**
     * Checks if its possible to get a certain hand
     * @param communityCards Cards on the table
     * @param p The player
     * @return True if a hand is possible to get
     */
    boolean canGetHand(Collection<Card> communityCards, Player p);

    /**
     * Calculates the probability that player p gets a specific hand
     * @param communityCards Cards on the table
     * @param p The player
     * @return The probability in the form of a double
     */
    double handProbability(Collection<Card> communityCards, Player p);

    /**
     * @return The hand type
     */
    HandType getType();
}
