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
	 * Makes a list of cards in your hand + cards on the table and sorts them.
	 * Should be used when calculating values.
	 * @return cards on table + hand.
	 */
	private ArrayList<Card> getCardsFromTableAndHand() {
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
		return cards;
	}
	
	private int evaluateCards(ArrayList<Card> cards) {
		ShowdownEvaluator se = new ShowdownEvaluator();
		Hand hand = se.evaluate(cards);
		switch(hand) {
		case ROYALFLUSH:
			return 100;
		case STRAIGHTFLUSH:
			return 95;
		case FOUROFAKIND:
			return 90;
		case FULLHOUSE:
			return 85;
		case FLUSH:
			return 80;
		case STRAIGHT:
			return 75;
		case THREEOFAKIND:
			return 70;
		case TWOPAIRS:
			return 60;
		case ONEPAIR:
			return 35;
		case HIGHCARD:
			return 0;
		default:
			return 0;
		}
	}
	
	/**
	 * Calculates the value of the cards currently on the table
	 * @return value (0-100)
	 */
	private int evaluateTable() {
		ArrayList<Card> cards = table.getCardOnTable();
		return evaluateCards(cards);
	}
	
	/**
	 * Calculates the value of the cards in your hand combined with the current table.
	 * @return value (0-100)
	 */
	private int evaluateHand() {
		ArrayList<Card> cards = getCardsFromTableAndHand();
		return evaluateCards(cards);
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
