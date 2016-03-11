package org.gruppe2.ai;

import java.util.ArrayList;
import java.util.Collections;

import org.gruppe2.backend.Action;
import org.gruppe2.backend.Card;
import org.gruppe2.backend.Player;
import org.gruppe2.backend.PokerTable;
import org.gruppe2.backend.ShowdownEvaluator;
import org.gruppe2.backend.ShowdownEvaluator.Hand;

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
		int value = 0;
		ArrayList<Card> cards = new ArrayList<Card>();
		if (card1 != null && card2 != null) {
			cards.add(card1);
			cards.add(card2);
		}
		if (table != null) {
			ArrayList<Card> cardsOnTable = table.getCardOnTable();
			for (Card c : cardsOnTable) {
				cards.add(c);
			}
		}
		Collections.sort(cards);
		ShowdownEvaluator se = new ShowdownEvaluator();
		Hand hand = se.evaluate(cards);
		if (hand.equals(Hand.ROYALFLUSH)) {
			value = 1;
		} else if (hand.equals(Hand.STRAIGHTFLUSH)) {
			value = 95;
		} else if (hand.equals(Hand.FOUROFAKIND)) {
			value = 90;
		} else if (hand.equals(Hand.FULLHOUSE)) {
			value = 85;
		} else if (hand.equals(Hand.FLUSH)) {
			value = 80;
		} else if (hand.equals(Hand.STRAIGHT)) {
			value = 75;
		} else if (hand.equals(Hand.THREEOFAKIND)) {
			value = 70;
		} else if (hand.equals(Hand.TWOPAIRS)) {
			value = 60;
		} else if (hand.equals(Hand.ONEPAIR)) {
			value = 35;
		} else if (hand.equals(Hand.HIGHCARD)) {
			value=0;
		}
		return value;
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
	/**
	 * Finds the cards included in the hand getting evaluated. 
	 * For example if AI got 4 of a kind, bestHand will be those 4 cards.
	 * This method is to help evaluate hand value.
	 * @return bestHand
	 */
	private ArrayList<Card> getCardsInBestHand(){
		ArrayList<Card> bestHand;
		return null;
	}
}
