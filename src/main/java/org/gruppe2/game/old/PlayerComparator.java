package org.gruppe2.game.old;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

class PlayerComparator implements Comparator<Player> {

	@Override
	public int compare(Player p1, Player p2) {
		ArrayList<Card> p1_Cards = new ArrayList<Card>();
		ArrayList<Card> p2_Cards = new ArrayList<Card>();

		p1_Cards.add(p1.getCard1());
		p1_Cards.add(p1.getCard2());
		p1_Cards.addAll(p1.getClient().getSession().getTable()
				.getCommunityCards());
		p2_Cards.add(p2.getCard1());
		p2_Cards.add(p2.getCard2());
		p2_Cards.addAll(p2.getClient().getSession().getTable()
				.getCommunityCards());

		ShowdownEvaluatorNew evaluator = new ShowdownEvaluatorNew();
		ArrayList<HandCollector> p1_hands = evaluator.evaluateHands(p1_Cards);
		ArrayList<HandCollector> p2_hands = evaluator.evaluateHands(p2_Cards);
		
		Iterator<HandCollector> p1_iterator = p1_hands.iterator();
		Iterator<HandCollector> p2_iterator = p2_hands.iterator();
		
		return compareHands(p1_iterator, p2_iterator);
	}
	
	public int compareHands(Iterator<HandCollector> p1_iterator, Iterator<HandCollector> p2_iterator) {
		
		//Handle special cases;
		if(p1_iterator.hasNext() && !p2_iterator.hasNext())
			return 1;
		else if(!p1_iterator.hasNext() && p2_iterator.hasNext())
			return -1;
		else if(!p1_iterator.hasNext() && !p2_iterator.hasNext())
			return 0;
		
		HandCollector p1_hand = p1_iterator.next();
		HandCollector p2_hand = p2_iterator.next();
		
		//Compare ranks (it's cheaper)
		if(p1_hand.getRank() > p2_hand.getRank())
			return 1;
		else if(p1_hand.getRank() < p2_hand.getRank())
			return -1;
		else {
			int compareCards = compareCards(p1_hand.getCards(), p2_hand.getCards(), p1_hand.getRank());
			if(compareCards == 0)
				return compareHands(p1_iterator, p2_iterator);
			else
				return compareCards;
		}
	}
	
	public int compareCards(List<Card> hand_1, List<Card> hand_2, int rank) {
		switch(rank) {
		case 1:
			return compareHighCard(hand_1, hand_2);
		case 2:
			return compareOnePair(hand_1, hand_2);
		case 3:
			return compareTwoPair(hand_1, hand_2);
		case 4:
			return compareThreeOfAKind(hand_1, hand_2);
		case 5:
			return compareStraight(hand_1, hand_2);
		case 6:
			return compareFlush(hand_1, hand_2);
		case 7:
			return compareFullHouse(hand_1, hand_2);
		case 8:
			return compareFourOfAKind(hand_1, hand_2);
		case 9:
			return compareStraightFlush(hand_1, hand_2);
		case 10:
			return 0;
		default:
			throw new IllegalArgumentException("Hand rank can't be above 10 or below 1");
		}
	}
	
	public int compareStraightFlush(List<Card> hand_1, List<Card> hand_2) {
		return combineFaceValue(hand_1, hand_2);
	}
	
	public int compareFourOfAKind(List<Card> hand_1, List<Card> hand_2) {
		return combineFaceValue(hand_1, hand_2);
	}
	
	public int compareFullHouse(List<Card> hand_1, List<Card> hand_2) {
		ShowdownEvaluatorNew se = new ShowdownEvaluatorNew();
		ArrayList<Card> bestHouse_1 = se.getBestThreeOfAKind(hand_1);
		ArrayList<Card> bestHouse_2 = se.getBestThreeOfAKind(hand_2);
		
		int compareThreeCards = compareThreeOfAKind(bestHouse_1, bestHouse_2);
		if(compareThreeCards != 0)
			return compareThreeCards;
		else{
			ArrayList<Card> copy_hand_1 = copyHand(hand_1);
			copy_hand_1.removeAll(bestHouse_1);
			ArrayList<Card> copy_hand_2 = copyHand(hand_2);
			copy_hand_2.removeAll(bestHouse_2);
			
			return compareTwoPair(copy_hand_1, copy_hand_2);
		}
	}
	
	public ArrayList<Card> copyHand(List<Card> hand) {
		ArrayList<Card> newHand = new ArrayList<>();
		
		for(int i = 0; i < hand.size(); i++)
			newHand.add(hand.get(i));
		
		return newHand;
	}
	
	public int compareFlush(List<Card> hand_1, List<Card> hand_2) {
		return compareHighCard(hand_1, hand_2);
	}
	
	public int compareStraight(List<Card> hand_1, List<Card> hand_2) {
		return compareHighCard(hand_1, hand_2);
	}
	
	public int compareThreeOfAKind(List<Card> hand_1, List<Card> hand_2) {
		return combineFaceValue(hand_1, hand_2);
	}
	
	public int compareTwoPair(List<Card> hand_1, List<Card> hand_2) {
		ShowdownEvaluatorNew se = new ShowdownEvaluatorNew();
		ArrayList<Card> bestTwoPair_1 = se.getBestOnePair(hand_1);
		ArrayList<Card> bestTwoPair_2 = se.getBestOnePair(hand_2);
		
		int compareBestPair = compareOnePair(bestTwoPair_1, bestTwoPair_2);
		if(compareBestPair != 0) // If they have the same top pair, continue...
			return compareBestPair;
		
		ArrayList<Card> copy_hand_1 = copyHand(hand_1);
		copy_hand_1.removeAll(bestTwoPair_1);
		ArrayList<Card> copy_hand_2 = copyHand(hand_2);
		copy_hand_2.removeAll(bestTwoPair_2);
		
		return compareOnePair(copy_hand_1, copy_hand_2);
	}
	
	public int compareOnePair(List<Card> hand_1, List<Card> hand_2) {
		return combineFaceValue(hand_1, hand_2);
	}
	
	public int combineFaceValue(List<Card> hand_1, List<Card> hand_2) {
		int hand_1_score = 0, hand_2_score = 0;
		for(Card c : hand_1)
			hand_1_score += c.getFaceValue();
		for(Card c : hand_2)
			hand_2_score += c.getFaceValue();
		return Integer.compare(hand_1_score, hand_2_score);
	}
	
	public int compareHighCard(List<Card> hand_1, List<Card> hand_2) {
		for(int i = 0; i < hand_1.size() && i < hand_2.size(); i++) {
			int comp = hand_1.get(i).compareTo(hand_2.get(i));
			if(comp != 0)
				return comp;
		}
		
		return 0;
	}
}