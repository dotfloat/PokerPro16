package org.gruppe2.game.old;

import java.util.Collections;
import java.util.List;

public class HandCollector implements Comparable<HandCollector> {
	int rank;
	List<Card> cards;
	
	public HandCollector(int rank, List<Card> cards) {
		this.rank = rank;
		this.cards = cards;
	}
	
	@Override
	public int compareTo(HandCollector o) {
		if(Integer.compare(this.rank, o.getRank()) != 0)
			return Integer.compare(this.rank, o.getRank());
		else{
			return compareCards(this.cards, o.getCards());
		}
	}
	
	private int compareCards(List<Card> hand_1, List<Card> hand_2) {
		switch(this.rank) {
		case 0:
			return compareHighCard(hand_1, hand_2);
		case 1:
			return compareOnePairCards(hand_1, hand_2);
		case 2:
			return compareTwoPairsCards(hand_1, hand_2);
		case 3:
			return compareHighCard(hand_1, hand_2);
		case 4:
			return compareHighCard(hand_1, hand_2);
		case 5:
			return compareHighCard(hand_1, hand_2);
		case 6:
			return compareHighCard(hand_1, hand_2);
		default:
			throw new IllegalArgumentException("An invalid hand was given");
		}
	}
	
	private int compareHighCard(List<Card> hand_1, List<Card> hand_2) {
		Collections.sort(hand_1);
		Collections.sort(hand_2);
		
		return hand_1.get(hand_1.size()-1).compareTo(hand_2.get(hand_2.size()-1));
	}
	private int compareOnePairCards(List<Card> hand_1, List<Card> hand_2) {
		return 0;
	}
	private int compareTwoPairsCards(List<Card> hand_1, List<Card> hand_2) {
		return 0;
	}
	private int compareThreeOfAKindCards(List<Card> hand_1, List<Card> hand_2) {
		return 0;
	}
	private int compareStraightCards(List<Card> hand_1, List<Card> hand_2) {
		return 0;
	}
	private int compareFlushCards(List<Card> hand_1, List<Card> hand_2) {
		return 0;
	}
	private int compareFullHouseCards(List<Card> hand_1, List<Card> hand_2) {
		return 0;
	}
	private int compareFourOfAKindCards(List<Card> hand_1, List<Card> hand_2) {
		return 0;
	}
	private int compareStraightFlushCard(List<Card> hand_1, List<Card> hand_2) {
		return 0;
	}
	private int compareRoyalFlushCard(List<Card> hand_1, List<Card> hand_2) {
		return 0;
	}
			
	public int getRank() {
		return rank;
	}
	
	public List<Card> getCards() {
		return cards;
	}
}
