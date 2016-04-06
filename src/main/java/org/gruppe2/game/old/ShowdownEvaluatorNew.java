package org.gruppe2.game.old;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gruppe2.game.old.Card.Suit;

public class ShowdownEvaluatorNew {

	public ArrayList<HandCollector> evaluateHands(List<Card> cards) {
		ArrayList<HandCollector> hands = new ArrayList<>();
		
		// My little quick fix; because I messed up the poker rules to begin with.
		// It looks terrible, but it works, I'll fix it later when brain functions again.
		hands.addAll(royalFlush(cards));
		if(hands.isEmpty())
			hands.addAll(straightFlush(cards));
		if(hands.isEmpty()) {
			hands.addAll(fourOfAKind(cards));
			if(!hands.isEmpty())
				hands.add(new HandCollector(1, getBestHighCard(cards)));
		}
		if(hands.isEmpty())
			hands.addAll(fullHouse(cards));
		if(hands.isEmpty())
			hands.addAll(flush(cards));
		if(hands.isEmpty())
			hands.addAll(straight(cards));
		if(hands.isEmpty()) {
			hands.addAll(threeOfAKind(cards));
			if(!hands.isEmpty()) {
				ArrayList<Card> pair = getBestOnePair(cards);
				if(pair.isEmpty()) {
					ArrayList<Card> bestCard = getBestHighCard(cards);
					hands.add(new HandCollector(1, bestCard));
					cards.removeAll(bestCard);
					hands.add(new HandCollector(1, getBestHighCard(cards)));
				}else
					hands.add(new HandCollector(2,pair));
			}
		}
		if(hands.isEmpty()) {
			hands.addAll(twoPair(cards));
			if(!hands.isEmpty())
				hands.add(new HandCollector(1, getBestHighCard(cards)));
		}
		if(hands.isEmpty()) {
			hands.addAll(onePair(cards));
			if(!hands.isEmpty()) {
				ArrayList<Card> bestCard = getBestHighCard(cards);
				hands.add(new HandCollector(1, bestCard));
				cards.removeAll(bestCard);
				ArrayList<Card> bestCard_2 = getBestHighCard(cards);
				hands.add(new HandCollector(1, bestCard_2));
				cards.removeAll(bestCard_2);
				hands.add(new HandCollector(1, getBestHighCard(cards)));
			}
		}
		if(hands.isEmpty()) {
			hands.addAll(highCard(cards));
		}
		
		return hands;
	}
	
	public List<Card> copy(List<Card> cards) {
		ArrayList<Card> newC = new ArrayList<Card>();
		for(int i = 0; i < cards.size(); i++)
			newC.add(cards.get(i));
		return newC;
	}
	
	public int evaluateTest(List<Card> cards) {
//		Collections.sort(cards);
		if (!royalFlush(cards).isEmpty())
			return 10;
		else if (!straightFlush(cards).isEmpty())
			return 9;
		else if (!fourOfAKind(cards).isEmpty())
			return 8;
		else if (!fullHouse(cards).isEmpty())
			return 7;
		else if (!flush(cards).isEmpty())
			return 6;
		else if (!straight(cards).isEmpty())
			return 5;
		else if (!threeOfAKind(cards).isEmpty())
			return 4;
		else if (!twoPair(cards).isEmpty())
			return 3;
		else if (!onePair(cards).isEmpty())
			return 2;
		else if (!highCard(cards).isEmpty())
			return 1;
		else {
			throw new IllegalArgumentException("Error, got an impossible hand!");
		}
	}
	
	public ArrayList<HandCollector> royalFlush(List<Card> cards) {
		ArrayList<HandCollector> listRoyalFlushHands = new ArrayList<>();

		ArrayList<Card> pair = null;
		while (!(pair = getBestroyalFlush(cards)).isEmpty()) {
			listRoyalFlushHands.add(new HandCollector(10, pair));
			cards.removeAll(pair);
		}

		return listRoyalFlushHands;
	}

	// TODO: This really needs a test!
	public ArrayList<Card> getBestroyalFlush(List<Card> cards) {
		ArrayList<Card> listOfCardsInRoyalFlush = getBestStraightFlush(cards);

		// Check if the straight flush has an Ace High
		if (!listOfCardsInRoyalFlush.isEmpty()
				&& getBestHighCard(listOfCardsInRoyalFlush).get(0).getFaceValue() != 14)
			listOfCardsInRoyalFlush.clear();

		return listOfCardsInRoyalFlush;
	}

	public ArrayList<HandCollector> straightFlush(
			List<Card> cards) {
		ArrayList<HandCollector> listStraightFlushHands = new ArrayList<>();

		ArrayList<Card> pair = null;
		while (!(pair = getBestStraightFlush(cards)).isEmpty()) {
			listStraightFlushHands.add(new HandCollector(9, pair));
			cards.removeAll(pair);
		}

		return listStraightFlushHands;
	}

	public ArrayList<Card> getBestStraightFlush(List<Card> cards) {
		ArrayList<Card> listOfCardsInStraightFlush = new ArrayList<>();
		ArrayList<Card> listOfCardsWithHighestSuit = new ArrayList<>();
		int diamonds = 0, hearts = 0, spades = 0, clubs = 0;

		// Count all suits:
		for (Card c : cards) {
			switch (c.getSuit()) {
			case DIAMONDS:
				diamonds++;
				break;
			case HEARTS:
				hearts++;
				break;
			case SPADES:
				spades++;
				break;
			case CLUBS:
				clubs++;
				break;
			}
		}

		// Check if any of the suits will result in a flush:
		if (diamonds >= 5)
			listOfCardsWithHighestSuit = cardsOfOneSuit(cards, Suit.DIAMONDS);
		else if (hearts >= 5)
			listOfCardsWithHighestSuit = cardsOfOneSuit(cards, Suit.HEARTS);
		else if (spades >= 5)
			listOfCardsWithHighestSuit = cardsOfOneSuit(cards, Suit.SPADES);
		else if (clubs >= 5)
			listOfCardsWithHighestSuit = cardsOfOneSuit(cards, Suit.CLUBS);

		listOfCardsInStraightFlush = getBestStraight(listOfCardsWithHighestSuit);

		return listOfCardsInStraightFlush;
	}

	private ArrayList<Card> cardsOfOneSuit(List<Card> cards, Suit suit) {
		ArrayList<Card> flushWithSuit = new ArrayList<>();
		Collections.sort(cards);

		// Reversed loop, starts with highest value card.
		for (int i = cards.size() - 1; i >= 0; i--) {
			if (flushWithSuit.size() == 5)
				break;

			if (cards.get(i).getSuit() == suit)
				flushWithSuit.add(cards.get(i));
		}

		// Ignore this, it's never supposed to run, unless previous code has
		// bugs!
		if (flushWithSuit.size() < 5)
			flushWithSuit.clear();

		return flushWithSuit;
	}

	public ArrayList<HandCollector> fourOfAKind(List<Card> cards) {
		ArrayList<HandCollector> listFourOfAKindHands = new ArrayList<>();

		ArrayList<Card> pair = null;
		while (!(pair = getBestFourOfAKind(cards)).isEmpty()) {
			listFourOfAKindHands.add(new HandCollector(8, pair));
			cards.removeAll(pair);
		}
		
		return listFourOfAKindHands;
	}

	public ArrayList<Card> getBestFourOfAKind(List<Card> cards) {
		Map<Integer, Suit[]> freq = cardFaceFrequencyTest(cards);
		ArrayList<Card> listOfCardsInFourOfAKind = new ArrayList<>();
		int highestCardValue = -1;

		for (Map.Entry<Integer, Suit[]> entry : freq.entrySet()) {
			// Does the list of cards contain 2 equal cards of value X?
			if (entry.getValue().length >= 4) {
				// Is the new pair X higher than other pairs found?
				if (entry.getKey() > highestCardValue) {
					listOfCardsInFourOfAKind.clear(); // Clear the list of cards
														// in old pair.
					highestCardValue = entry.getKey();
					for (Suit s : entry.getValue())
						// Add all cards in new pair.
						listOfCardsInFourOfAKind
								.add(new Card(entry.getKey(), s));
				}
			}
		}

		return listOfCardsInFourOfAKind;
	}

	public ArrayList<HandCollector> fullHouse(List<Card> cards) {
		ArrayList<HandCollector> listFullHouseHands = new ArrayList<>();

		ArrayList<Card> pair = null;
		while (!(pair = getBestFullHouse(cards)).isEmpty()) {
			listFullHouseHands.add(new HandCollector(7, pair));
			cards.removeAll(pair);
		}

		return listFullHouseHands;
	}

	public ArrayList<Card> getBestFullHouse(List<Card> cards) {
		ArrayList<Card> listOfCardsInFullHouse = new ArrayList<>();

		ArrayList<Card> highestThreeOfAKind = getBestThreeOfAKind(cards);
		ArrayList<Card> cardsExcludingThreeOfAKind = new ArrayList<>(); // Mostly
																		// a
																		// dummy
																		// list
		for (Card c : cards)
			if (!highestThreeOfAKind.contains(c))
				cardsExcludingThreeOfAKind.add(c);
		ArrayList<Card> highestOnePair = getBestOnePair(cardsExcludingThreeOfAKind);

		// If we found a full house, add it, if not, then we return an empty
		// list
		if (!highestThreeOfAKind.isEmpty() && !highestOnePair.isEmpty()) {
			listOfCardsInFullHouse.addAll(highestThreeOfAKind);
			listOfCardsInFullHouse.addAll(highestOnePair);
		}

		return listOfCardsInFullHouse;
	}

	public ArrayList<HandCollector> flush(List<Card> cards) {
		ArrayList<HandCollector> listFlushHands = new ArrayList<>();

		ArrayList<Card> pair = null;
		while (!(pair = getBestFlush(cards)).isEmpty()) {
			listFlushHands.add(new HandCollector(6, pair));
			cards.removeAll(pair);
		}

		return listFlushHands;
	}

	// This code breaks if there are too many cards in hand or on table! (Hand >
	// 2 or Table > 5)
	public ArrayList<Card> getBestFlush(List<Card> cards) {
		ArrayList<Card> listOfCardsInFlush = new ArrayList<>();
		int diamonds = 0, hearts = 0, spades = 0, clubs = 0;

		// Count all suits:
		for (Card c : cards) {
			switch (c.getSuit()) {
			case DIAMONDS:
				diamonds++;
				break;
			case HEARTS:
				hearts++;
				break;
			case SPADES:
				spades++;
				break;
			case CLUBS:
				clubs++;
				break;
			}
		}

		// Check if any of the suits will result in a flush:
		if (diamonds >= 5)
			listOfCardsInFlush = flushCardsFromSuit(cards, Suit.DIAMONDS);
		else if (hearts >= 5)
			listOfCardsInFlush = flushCardsFromSuit(cards, Suit.HEARTS);
		else if (spades >= 5)
			listOfCardsInFlush = flushCardsFromSuit(cards, Suit.SPADES);
		else if (clubs >= 5)
			listOfCardsInFlush = flushCardsFromSuit(cards, Suit.CLUBS);

		return listOfCardsInFlush;
	}

	private ArrayList<Card> flushCardsFromSuit(List<Card> cards, Suit suit) {
		ArrayList<Card> flushWithSuit = new ArrayList<>();
		Collections.sort(cards);

		// Reversed loop, starts with highest value card.
		for (int i = cards.size() - 1; i >= 0; i--) {
			if (flushWithSuit.size() == 5)
				break;

			if (cards.get(i).getSuit() == suit)
				flushWithSuit.add(cards.get(i));
		}

		// Ignore this, it's never supposed to run, unless previous code has
		// bugs!
		if (flushWithSuit.size() < 5)
			flushWithSuit.clear();

		return flushWithSuit;
	}

	public ArrayList<HandCollector> straight(List<Card> cards) {
		ArrayList<HandCollector> listStraightHands = new ArrayList<>();

		ArrayList<Card> pair = null;
		while (!(pair = getBestStraight(cards)).isEmpty()) {
			listStraightHands.add(new HandCollector(5, pair));
			cards.removeAll(pair);
		}

		return listStraightHands;
	}

	// TODO: Method up for change!
	public ArrayList<Card> getBestStraight(List<Card> cards) {
		ArrayList<Card> listOfCardsInStraight = new ArrayList<>();
		if (cards.size() >= 1) {
			Collections.sort(cards);
			int cardsInARow = 1; // Starter card always counts.

			// Face value of highest card
			int lastCardValue = cards.get(cards.size() - 1).getFaceValue();
			listOfCardsInStraight.add(cards.get(cards.size() - 1));

			// Simple reversed loop, skipping the highest card.
			for (int i = (cards.size() - 2); (i + 1) > 0; i--) {
				if (listOfCardsInStraight.size() == 5)
					break;

				// If the next card is 1 less than the previous, then it counts!
				if ((cards.get(i).getFaceValue() + 1) == lastCardValue) {
					cardsInARow++;
					listOfCardsInStraight.add(cards.get(i));
				} else if ((cards.get(i).getFaceValue() + 1) < lastCardValue) {
					listOfCardsInStraight.clear();
					cardsInARow = 1;
					listOfCardsInStraight.add(cards.get(i));
				}

				lastCardValue = cards.get(i).getFaceValue();
			}

			// Special case for Ace as it can also count as a "1"
			if (lastCardValue == 2 && cardsInARow == 4
					&& cards.get(cards.size() - 1).getFaceValue() == 14)
				listOfCardsInStraight.add(cards.get(cards.size() - 1));

			if (listOfCardsInStraight.size() != 5)
				listOfCardsInStraight.clear();

		}

		return listOfCardsInStraight;
	}

	public ArrayList<HandCollector> threeOfAKind(List<Card> cards) {
		ArrayList<HandCollector> listThreeOfAKindHands = new ArrayList<>();

		ArrayList<Card> pair = null;
		while (!(pair = getBestThreeOfAKind(cards)).isEmpty()) {
			listThreeOfAKindHands.add(new HandCollector(4, pair));
			cards.removeAll(pair);
		}

		return listThreeOfAKindHands;
	}

	public ArrayList<Card> getBestThreeOfAKind(List<Card> cards) {
		Map<Integer, Suit[]> freq = cardFaceFrequencyTest(cards);
		ArrayList<Card> listOfCardsInThreeOfAKind = new ArrayList<>();
		int highestCardValue = -1;

		for (Map.Entry<Integer, Suit[]> entry : freq.entrySet()) {
			// Does the list of cards contain 2 equal cards of value X?
			if (entry.getValue().length >= 3) {
				// Is the new pair X higher than other pairs found?
				if (entry.getKey() > highestCardValue) {
					listOfCardsInThreeOfAKind.clear(); // Clear the list of
														// cards in old pair.
					highestCardValue = entry.getKey();
					for (Suit s : entry.getValue())
						// Add all cards in new pair.
						listOfCardsInThreeOfAKind.add(new Card(entry.getKey(),
								s));
				}
			}
		}

		return listOfCardsInThreeOfAKind;
	}

	public ArrayList<HandCollector> twoPair(List<Card> cards) {
		ArrayList<HandCollector> listTwoPairHands = new ArrayList<>();

		ArrayList<Card> pair = null;
		while (!(pair = getBestTwoPair(cards)).isEmpty()) {
			listTwoPairHands.add(new HandCollector(3, pair));
			cards.removeAll(pair);
		}

		return listTwoPairHands;
	}

	public ArrayList<Card> getBestTwoPair(List<Card> cards) {
		ArrayList<Card> listOfCardsInTwoPair = new ArrayList<>();

		ArrayList<Card> highestPair = getBestOnePair(cards); // Get the highest
																// pair of cards
		ArrayList<Card> cardsExcludingHighestPair = new ArrayList<>(); // Mostly
																		// a
																		// dummy
																		// list
		for (Card c : cards)
			if (!highestPair.contains(c))
				cardsExcludingHighestPair.add(c);
		ArrayList<Card> lowestPair = getBestOnePair(cardsExcludingHighestPair);

		// If we found two pairs, add them, if not, then we return an empty list
		if (!highestPair.isEmpty() && !lowestPair.isEmpty()) {
			listOfCardsInTwoPair.addAll(highestPair);
			listOfCardsInTwoPair.addAll(lowestPair);
		}

		return listOfCardsInTwoPair;
	}

	// TODO: Bugfix, it returns too many cards.
	public ArrayList<HandCollector> onePair(List<Card> cards) {
		ArrayList<HandCollector> listPairHands = new ArrayList<>();

		ArrayList<Card> pair = null;
		while (!(pair = getBestOnePair(cards)).isEmpty()) {
			listPairHands.add(new HandCollector(2, pair));
			cards.removeAll(pair);
		}

		return listPairHands;
	}

	public ArrayList<Card> getBestOnePair(List<Card> cards) {
		Map<Integer, Suit[]> freq = cardFaceFrequencyTest(cards);
		ArrayList<Card> listOfCardsInPair = new ArrayList<>();

		int highestCardValue = -1;

		for (Map.Entry<Integer, Suit[]> entry : freq.entrySet()) {
			// Does the list of cards contain 2 equal cards of value X?
			if (entry.getValue().length >= 2) {
				// Is the new pair X higher than other pairs found?
				if (entry.getKey() > highestCardValue) {
					listOfCardsInPair = new ArrayList<>(); // Clear the list of
															// cards in old
															// pair.
					highestCardValue = entry.getKey();
					for (Suit s : entry.getValue())
						if(listOfCardsInPair.size() < 2)
						// Add all cards in new pair.
						listOfCardsInPair.add(new Card(entry.getKey(), s));
				}
			}
		}

		return listOfCardsInPair;
	}

	/*
	 * public ArrayList<Card> onePairTest(List<Card> cards) { Map<Integer,
	 * Suit[]> freq = cardFaceFrequencyTest(cards); ArrayList<Card>
	 * listOfCardsInPair = new ArrayList<>(); int highestCardValue = -1;
	 * 
	 * for (Map.Entry<Integer, Suit[]> entry : freq.entrySet()) { // Does the
	 * list of cards contain 2 equal cards of value X? if
	 * (entry.getValue().length >= 2) { // Is the new pair X higher than other
	 * pairs found? if(entry.getKey() > highestCardValue) {
	 * listOfCardsInPair.clear(); // Clear the list of cards in old pair.
	 * highestCardValue = entry.getKey(); for(Suit s : entry.getValue()) // Add
	 * all cards in new pair. listOfCardsInPair.add(new Card(entry.getKey(),s));
	 * } } }
	 * 
	 * return listOfCardsInPair; }
	 */

	public ArrayList<HandCollector> highCard(List<Card> cards) {
		ArrayList<HandCollector> listOfCardsInHighCard = new ArrayList<>();
		Collections.sort(cards);
		if (!cards.isEmpty())
			for (int i = cards.size()-1; i >= 0; i--) {
				ArrayList<Card> highCard = new ArrayList<>();
				highCard.add(cards.get(i));
				listOfCardsInHighCard.add(new HandCollector(1,
						highCard));
			}
		return listOfCardsInHighCard;
	}
	
	public ArrayList<Card> getBestHighCard(List<Card> cards) {
		ArrayList<Card> highestCard = new ArrayList<>();
		
		Collections.sort(cards);
		highestCard.add(cards.get(cards.size()-1));
		
		return highestCard;
	}
	
	private Map<Integer, Suit[]> cardFaceFrequencyTest(List<Card> cards) {
		HashMap<Integer, Suit[]> freq = new HashMap<>();

		for (Card c : cards) {
			if (c != null) {
				int face = c.getFaceValue();

				freq.put(face, addSuitToSuitArray(freq.get(face), c.getSuit()));
			}
		}

		return freq;
	}
	
	private Suit[] addSuitToSuitArray(Suit[] oldSuit, Suit s) {
		if (oldSuit == null)
			return new Suit[] { s };
		else {
			Suit[] newSuit = new Suit[oldSuit.length + 1];
			for (int i = 0; i < oldSuit.length; i++) {
				newSuit[i] = oldSuit[i];
			}
			newSuit[oldSuit.length] = s;
			return newSuit;
		}
	}
	
	public ArrayList<Player> getWinnerOfRound(List<Player> players) {
		ArrayList<Player> winners = new ArrayList<>();
		for(Player p : players)
			winners.add(p);
				
		PlayerComparator comp = new PlayerComparator();
		for(int i = 0; i < winners.size()-1; i++) {
			switch(comp.compare(winners.get(i), winners.get(i+1))) {
			case -1:
				winners.remove(i);
				break;
			case 1:
				winners.remove(i+1);
				break;
			case 0:
				break;
			}
		}
		
		return winners;
	}
}

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