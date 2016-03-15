package org.gruppe2.ai;

import java.util.List;

import org.gruppe2.backend.Card;
import org.gruppe2.backend.Player;
import org.gruppe2.backend.GameSession;
import org.gruppe2.backend.ShowdownEvaluator;

public class OtherPlayersEvaluator implements AIEvaluate {

	@Override
	public double evaluate(GameSession session, Player bot) {
		int numberOfPlayers = session.getPlayers().size();
		int value=0;
		List<Card> cards = session.getTable().getCommunityCards();
		ShowdownEvaluator evaluator = new ShowdownEvaluator();
		value+=calculateStraightFlush(numberOfPlayers, cards, evaluator);
		value+=calculateFourOfAKind(numberOfPlayers, cards, evaluator);
		value+=calculateFullHouse(numberOfPlayers, cards, evaluator);
		value+=calculateFlush(numberOfPlayers, cards, evaluator);
		value+=calculateStraight(numberOfPlayers, cards, evaluator);
		value+=calculateThreeOfAKind(numberOfPlayers, cards, evaluator);
		value+=calculateTwoPair(numberOfPlayers, cards, evaluator);
		value+=calculateOnePair(numberOfPlayers, cards, evaluator);
		return value;
	}
	/**
	 * Calculates the chance to get a Straight Flush before the game round ends.
	 * @return chance (0-100)
	 */
	private double calculateStraightFlush(int players, List<Card> cards, ShowdownEvaluator evaluator) {
		if(evaluator.straightFlush(cards))
			return 100;
		else{
			return 0;
		}
	}
	
	/**
	 * Calculates the chance to get a Four of a Kind before the game round ends.
	 * @return chance (0-100)
	 */
	private double calculateFourOfAKind(int players, List<Card> cards, ShowdownEvaluator evaluator) {
		if(evaluator.fourOfAKind(cards))
			return 100;
		else{
			return 0;
		}
	}
	
	/**
	 * Calculates the chance to get a Full House before the game round ends.
	 * @return chance (0-100)
	 */
	private double calculateFullHouse(int players, List<Card> cards, ShowdownEvaluator evaluator) {
		if(evaluator.fullHouse(cards))
			return 100;
		else{
			return 0;
		}
	}
	
	/**
	 * Calculates the chance to get a Flush before the game round ends.
	 * @return chance (0-100)
	 */
	private double calculateFlush(int players, List<Card> cards, ShowdownEvaluator evaluator) {
		if(evaluator.flush(cards))
			return 100;
		else{
			return 0;
		}
	}
	
	/**
	 * Calculates the chance to get a Straight before the game round ends.
	 * @return chance (0-100)
	 */
	private double calculateStraight(int players, List<Card> cards, ShowdownEvaluator evaluator) {
		if(evaluator.straight(cards))
			return 100;
		else{
			return 0;
		}
	}
	
	/**
	 * Calculates the chance to get a Three of a Kind before the game round ends.
	 * @return chance (0-100)
	 */
	private double calculateThreeOfAKind(int players, List<Card> cards, ShowdownEvaluator evaluator) {
		if(evaluator.twoPair(cards))
			return 100;
		else{
			return 0;
		}
	}
	
	/**
	 * Calculates the chance to get Two Pair before the game round ends.
	 * @return chance (0-100)
	 */
	private double calculateTwoPair(int players, List<Card> cards, ShowdownEvaluator evaluator) {
		if(evaluator.twoPair(cards))
			return 100;
		else{
			return calculateOnePair(players, cards, evaluator)*(1/13)*(players*2+cards.size()-2);
		}
	}
	
	/**
	 * Calculates the chance to get a Pair before the game round ends.
	 * @return chance (0-100)
	 */
	private double calculateOnePair(int players, List<Card> cards, ShowdownEvaluator evaluator) {
		if(evaluator.onePair(cards))
			return 100;
		else{
			return (1/13)*(players*2+cards.size());
		}
	}

}
