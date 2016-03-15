package org.gruppe2.ai;

import java.util.ArrayList;
import java.util.List;

import org.gruppe2.backend.Card;
import org.gruppe2.backend.Player;
import org.gruppe2.backend.GameSession;
import org.gruppe2.backend.ShowdownEvaluator;

public class OtherPlayersEvaluator implements AIEvaluate {

	/**
	 * Evaluates the odds that some other player will get a better hand than you.
	 * 
	 * @return double - chance to lose with current hand.
	 */
	@Override
	public double evaluate(GameSession session, Player bot) {
		int numberOfPlayers = session.getPlayers().size();
		int value=0;
		List<Card> cards = session.getTable().getCommunityCards();
		ShowdownEvaluator evaluator = new ShowdownEvaluator();
		List<Card> botCards = new ArrayList<>();
		botCards.add(bot.getCard1());
		botCards.add(bot.getCard2());
		
		// There's not supposed to be any "break;" in this switch statement.
		switch(evaluator.evaluate(botCards)) {
		case HIGHCARD:
			value+=calculateHighCard(numberOfPlayers, cards, botCards, evaluator);
		case ONEPAIR:
			value+=calculateOnePair(numberOfPlayers, cards, evaluator);
		case TWOPAIRS:
			value+=calculateTwoPair(numberOfPlayers, cards, evaluator);
		case THREEOFAKIND:
			value+=calculateThreeOfAKind(numberOfPlayers, cards, evaluator);
		case STRAIGHT:
			value+=calculateStraight(numberOfPlayers, cards, evaluator);
		case FLUSH:
			value+=calculateFlush(numberOfPlayers, cards, evaluator);
		case FULLHOUSE:
			value+=calculateFullHouse(numberOfPlayers, cards, evaluator);
		case FOUROFAKIND:
			value+=calculateFourOfAKind(numberOfPlayers, cards, evaluator);
		case STRAIGHTFLUSH:
			value+=calculateStraightFlush(numberOfPlayers, cards, evaluator);
		case ROYALFLUSH:
			value+=calculateRoyalFlush(numberOfPlayers, cards, evaluator);
		default:
			break;
		}
		return value;
	}
	
	/**
	 * Calculates the chance to get a Royal Flush before the game round ends.
	 * @return chance (0-100)
	 */
	private double calculateRoyalFlush(int players, List<Card> cards, ShowdownEvaluator evaluator) {
		if(evaluator.royalFlush(cards))
			return 100;
		else{
			return 0;
		}
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
			return calculateOnePair(players, cards, evaluator)*(1/13)*((players-2)*2+cards.size()-2);
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
			return (1/13)*((players-2)*2+cards.size());
		}
	}
	
	/**
	 * Calculates the chance that other players has a better High Card.
	 * @return chance (0-100)
	 */
	private double calculateHighCard(int players, List<Card> cards, List<Card> botCards, ShowdownEvaluator evaluator) {
		return (1 - Math.max(botCards.get(0).getFaceValue(), botCards.get(0).getFaceValue())*(1/13))*players*2;
	}
}
