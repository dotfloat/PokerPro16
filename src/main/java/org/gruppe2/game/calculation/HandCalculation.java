package org.gruppe2.game.calculation;

import org.gruppe2.game.Card;
import org.gruppe2.game.Hand;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

interface HandCalculation extends Comparator<List<Card>> {
    /**
     * Check if the given list of cards is a certain hand (ie. if it's a Pair)
     * @param cards a collection of 7 cards
     * @return true if it's a valid hand, false otherwise
     */
    boolean isHand(List<Card> cards);

    /**
     * Get the best 5 cards out of the 7 available
     * @param cards a list with 7 cards
     * @return A list with 5 elements or null if it's impossible to get the hand
     */
    Collection<Card> getBestCards(List<Card> cards);

    /**
     * Checks if its possible to get a certain hand when there are < 7 cards
     * @param cards a collection of [5, 7) cards to evaluate
     * @return true if a hand is possible to get, false otherwise
     */
    boolean canGet(List<Card> cards);

    /**
     * Calculates the probability that player p gets a specific hand
     * @param player The player model for the round
     * @return The probability as a double in the range 0..1
     */
    double probability(List<Card> cards);

    /**
     * @return The hand type
     */
    Hand getType();
}
