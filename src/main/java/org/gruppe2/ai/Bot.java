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
	private ArrayList<AIEvaluate> evaluators = new ArrayList<>();

	/**
	 * Initializes a Player (as a bot)
	 * 
	 * @param startChips
	 * @param table
	 */
	public Bot(int startChips, PokerTable table) {
		super(startChips, table);
		name = "AI";

		evaluators.add(new HandEvaluator());
	}

	public Action onTurn() {
		int strength = 0;

		for (AIEvaluate eval : evaluators) {
			strength += eval.evaluate(table, this);
		}

		if (strength > 50) {
			return new Action(Action.Type.ADD_CHIPS, 0);
		}
		
		return new Action(Action.Type.FOLD);
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
