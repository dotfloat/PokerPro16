package org.gruppe2.ai;

import org.gruppe2.backend.Player;
import org.gruppe2.backend.PokerTable;

public class OtherPlayersEvaluator implements AIEvaluate {

	@Override
	public double evaluate(PokerTable table, Player bot) {
		int numberOfPlayers = table.getPlayers().size();
		int value=0;
		value+=calculateStraightFlush(numberOfPlayers);
		value+=calculateFourOfAKind(numberOfPlayers);
		value+=calculateFullHouse(numberOfPlayers);
		value+=calculateFlush(numberOfPlayers);
		value+=calculateStraight(numberOfPlayers);
		value+=calculateThreeOfAKind(numberOfPlayers);
		value+=calculateTwoPair(numberOfPlayers);
		value+=calculateOnePair(numberOfPlayers);
		return value;
	}
	/**
	 * Calculates the chance to get a Straight Flush before the game round ends.
	 * @return chance (0-100)
	 */
	private double calculateStraightFlush(int players) {
		// TODO: Implement calculation
		return 0;
	}
	
	/**
	 * Calculates the chance to get a Four of a Kind before the game round ends.
	 * @return chance (0-100)
	 */
	private double calculateFourOfAKind(int players) {
		// TODO: Implement calculation
		return 0;
	}
	
	/**
	 * Calculates the chance to get a Full House before the game round ends.
	 * @return chance (0-100)
	 */
	private double calculateFullHouse(int players) {
		// TODO: Implement calculation
		return 0;
	}
	
	/**
	 * Calculates the chance to get a Flush before the game round ends.
	 * @return chance (0-100)
	 */
	private double calculateFlush(int players) {
		// TODO: Implement calculation
		return 0;
	}
	
	/**
	 * Calculates the chance to get a Straight before the game round ends.
	 * @return chance (0-100)
	 */
	private double calculateStraight(int players) {
		// TODO: Implement calculation
		return 0;
	}
	
	/**
	 * Calculates the chance to get a Three of a Kind before the game round ends.
	 * @return chance (0-100)
	 */
	private double calculateThreeOfAKind(int players) {
		// TODO: Implement calculation
		return 0;
	}
	
	/**
	 * Calculates the chance to get Two Pair before the game round ends.
	 * @return chance (0-100)
	 */
	private double calculateTwoPair(int players) {
		// TODO: Implement calculation
		return 0;
	}
	
	/**
	 * Calculates the chance to get a Pair before the game round ends.
	 * @return chance (0-100)
	 */
	private double calculateOnePair(int players) {
		// TODO: Implement calculation
		return 0;
	}

}
