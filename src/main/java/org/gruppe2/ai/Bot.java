package org.gruppe2.ai;

import org.gruppe2.backend.Action;
import org.gruppe2.backend.Player;
import org.gruppe2.backend.PokerTable;

public class Bot extends Player {

	/**
	 * Initializes a Player (as a bot)
	 * 
	 * @param startChips
	 * @param table
	 */
	public Bot(int startChips, PokerTable table) {
		super(startChips, table);
		name = "AI";
	}
	
	/**
	 * Do an action.
	 */
	@Override
	public void play(Action action) {
		// TODO: Actions.
		choice = action;
	}
	
	/**
	 * Calculates the value of the cards currently on the table
	 * @return value (0-100)
	 */
	private int calculateTableValue() {
		// TODO: Implement the calculator
		return 0;
	}
	
	/**
	 * Calculates the value of the cards in your hand combined with the current table.
	 * @return value (0-100)
	 */
	private int calculateHandValue() {
		// Maybe make it add all of the other calculators together?
//		int value = 0;
//		value = calculateStraightFlush() + etc..value.
		// TODO: Implement the calculator
		return 0;
	}
	
	/**
	 * Calculates the chance to get a Straight Flush before the game round ends.
	 * @return chance (0-100)
	 */
	private int calculateStraightFlush() {
		// TODO: Implement calculation
		return 0;
	}
	
	/**
	 * Calculates the chance to get a Four of a Kind before the game round ends.
	 * @return chance (0-100)
	 */
	private int calculateFourOfAKind() {
		// TODO: Implement calculation
		return 0;
	}
	
	/**
	 * Calculates the chance to get a Full House before the game round ends.
	 * @return chance (0-100)
	 */
	private int calculateFullHouse() {
		// TODO: Implement calculation
		return 0;
	}
	
	/**
	 * Calculates the chance to get a Flush before the game round ends.
	 * @return chance (0-100)
	 */
	private int calculateFlush() {
		// TODO: Implement calculation
		return 0;
	}
	
	/**
	 * Calculates the chance to get a Straight before the game round ends.
	 * @return chance (0-100)
	 */
	private int calculateStraight() {
		// TODO: Implement calculation
		return 0;
	}
	
	/**
	 * Calculates the chance to get a Three of a Kind before the game round ends.
	 * @return chance (0-100)
	 */
	private int calculateThreeOfAKind() {
		// TODO: Implement calculation
		return 0;
	}
	
	/**
	 * Calculates the chance to get Two Pair before the game round ends.
	 * @return chance (0-100)
	 */
	private int calculateTwoPair() {
		// TODO: Implement calculation
		return 0;
	}
	
	/**
	 * Calculates the chance to get a Pair before the game round ends.
	 * @return chance (0-100)
	 */
	private int calculateOnePair() {
		// TODO: Implement calculation
		return 0;
	}
}
