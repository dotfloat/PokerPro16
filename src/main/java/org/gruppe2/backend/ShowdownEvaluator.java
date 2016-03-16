package org.gruppe2.backend;

import java.util.*;

import org.gruppe2.backend.Card.Suit;

/**
 * A class for evaluating hands at showdown. Can be used to evaluate for
 * specific hands, or by the evaluate method which returns the best hand form
 * the cards.
 */
public class ShowdownEvaluator {
	Evaluated evaluated = null;

	/**
	 * Possible hands sorted from lowest to highest value.
	 *
	 * This makes the comparison more idiomatic, ie:
	 * <code>Hand.HIGHCARD < Hand.ROYALFLUSH</code>
	 */
	public enum Hand {
		HIGHCARD, ONEPAIR, TWOPAIRS, THREEOFAKIND, STRAIGHT, FLUSH, FULLHOUSE, FOUROFAKIND, STRAIGHTFLUSH, ROYALFLUSH
	}

	/**
	 * Method used to evaluate cards. Evaluated from best to worst. Returns the
	 * best hand.
	 * 
	 * @param cards
	 *            cards to evaluate
	 * @return enum Hand, best hand
	 */

	public Evaluated evaluate(List<Card> cards) {
		Collections.sort(cards);
		// done
		if (royalFlush(cards)){
			evaluated.setHand(Hand.ROYALFLUSH);
			return evaluated;
		}
		// done
		else if (straightFlush(cards)){
			evaluated.setHand(Hand.STRAIGHTFLUSH);
			return evaluated;
		}
		// done
		else if (fourOfAKind(cards)){
			evaluated.setHand(Hand.FOUROFAKIND);
			return evaluated;
		}
		// done
		else if (fullHouse(cards)){
			evaluated.setHand(Hand.FULLHOUSE);
			return evaluated;
		}
		// done
		else if (flush(cards)){
			evaluated.setHand(Hand.FLUSH);
			return evaluated;
		}
		// done
		else if (straight(cards)){
			evaluated.setHand(Hand.STRAIGHT);
			return evaluated;
		}
		// done
		else if (threeOfAKind(cards)){
			evaluated.setHand(Hand.THREEOFAKIND);
			return evaluated;
		}
		// done
		else if (twoPair(cards)){
			evaluated.setHand(Hand.TWOPAIRS);
			return evaluated;
		}
		// done
		else if (onePair(cards)){
			evaluated.setHand(Hand.ONEPAIR);
			return evaluated;
		}
		// done
		else if (highCard(cards)){
			evaluated.setHand(Hand.HIGHCARD);
			return evaluated;
		}
		// done
		else {
			System.out.println("Theses cards did not get evaluated correctly");
			return evaluated;
		}
	}

	/**
	 * Check if given cards makes a royal flush. A straight from a ten to an ace
	 * with all five cards of the same suit.
	 * 
	 * @param cards
	 *            cards to check
	 * @return true if it is a royal flush, false if not
	 */
	public boolean royalFlush(List<Card> cards) {
		if (straightFlush(cards)) {
			evaluated = new Evaluated();
			Collections.sort(cards);

			// using a set to remove duplicates
			Set<Integer> cardSet = new HashSet<>();

			for (Card card : cards) {
				if (card.getFaceValue() >= 10)
					cardSet.add(card.getFaceValue());
			}

			if (cardSet.size() == 5) {
				// Start of evaluated logic
				int i = Collections.max(cardSet);
				int[] high = new int[1];
				high[0] = i;
				evaluated.addHand(Hand.ROYALFLUSH, high);
				// end of evaluated logic
				return true;
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * Check if given cards makes a straight flush. Any straight with all five
	 * cards of the same suit.
	 * 
	 * @param cards
	 *            cards to check
	 * @return true if it is a straight flush, false if not
	 */
	public boolean straightFlush(List<Card> cards) {
		if (flush(cards) && straight(cards)) {
			evaluated = new Evaluated();
			Card lastCard = null;
			int count = 0;
			Collections.sort(cards);
			// checks if there is 5 cards with same suit and following numbers
			// in the sorted list
			for (Card c : cards) {
				if (lastCard != null) {

					if (c.getFaceValue() == lastCard.getFaceValue() + 1 && c.getSuit() == lastCard.getSuit()
							|| c.getFaceValue() == 2 && lastCard.getFaceValue() == 14) {
						count++;
						lastCard = c;
						if (count == 5) {
							// Start of evaluated logic
							int[] high = new int[1];
							high[0] = lastCard.getFaceValue();
							evaluated.addHand(Hand.STRAIGHTFLUSH, high);
							// end of evaluated logic
							return true;
						}
						continue;

					} else if (c.getFaceValue() == lastCard.getFaceValue()) {
						continue;

					} else {
						count = 1;
						lastCard = c;
					}

				} else {
					lastCard = c;
					count++;
				}
			}
		}
		return false;
	}

	/**
	 * Check if given cards makes four of a kind. Any four cards of the same
	 * rank.
	 * 
	 * @param cards
	 *            cards to check
	 * @return true if four of a kind, false if not
	 */
	public boolean fourOfAKind(List<Card> cards) {
		HashMap<Integer, Integer> map = new HashMap<>();
		evaluated = new Evaluated();

		for (Card card : cards) {
			if (map.containsKey(card.getFaceValue())) {
				int add = map.get(card.getFaceValue());
				map.replace(card.getFaceValue(), add + 1);
			} else {
				map.put(card.getFaceValue(), 1);
			}
		}

		boolean isFourOfAKind = false;
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 4) {
				isFourOfAKind = true;
			}
		}
		// Start of evaluated logic
		if (isFourOfAKind) {
			int[] high = new int[2];
			int high1 = 0;
			int high2 = 0;
			for (int i = 1; i < 15; i++) {
				if (map.get(i) == null)
					continue;
				if (map.get(i) == 4) {
					high[0] = i;
				}
				if (map.get(i) != 0) {
					if (i > high1) {
						high2 = high1;
						high1 = i;
					} else if (i > high2 && i != high1) {
						high2 = i;
					}
				}
			}
			if (high[0] == high1) {
				high[1] = high2;
			} else
				high[1] = high1;
			evaluated.addHand(Hand.FOUROFAKIND, high);
		}
		// end of evaluated logic

		return isFourOfAKind;
	}

	/**
	 * Check if given cards makes a full house. Any three cards of the same rank
	 * together with any two cards of the same rank.
	 * 
	 * @param cards
	 *            cards to check
	 * @return true for full house, false if not a full house
	 */
	public boolean fullHouse(List<Card> cards) {
		if (threeOfAKind(cards) && onePair(cards)) {
			// Start of evaluated logic
			evaluated = new Evaluated();
			HashMap<Integer, Integer> map = new HashMap<>();
			int[] high = new int[2];
			for (Card card : cards) {
				if (map.containsKey(card.getFaceValue())) {
					int add = map.get(card.getFaceValue());
					map.replace(card.getFaceValue(), add + 1);
				} else {
					map.put(card.getFaceValue(), 1);
				}
			}
			ArrayList<Integer> highs = new ArrayList<>();
			for (int i = 1; i < 15; i++) {
				if (map.get(i) == null)
					continue;
				if (map.get(i) == 3)
					highs.add(map.get(i));
				if (map.get(i) == 2)
					highs.add(map.get(i));
			}
			if (highs.size() > 2) {
				Collections.sort(highs);
			}
			high[0] = highs.get(highs.size() - 1);
			high[0] = highs.get(highs.size() - 2);
			evaluated.addHand(Hand.FULLHOUSE, high);
			// end of evaluated logic
			return true;
		}
		return false;
	}

	/**
	 * Check if given cards makes a flush. Any 5 cards of the same suit.
	 * 
	 * @param cards
	 *            list of cards to check
	 * @return true for flush, false if not flush
	 */
	public boolean flush(List<Card> cards) {
		boolean isFlush = false;
		HashMap<Card.Suit, Integer> map = new HashMap<>();
		evaluated = new Evaluated();

		for (Card card : cards) {
			if (map.containsKey(card.getSuit())) {
				int add = map.get(card.getSuit());
				map.replace(card.getSuit(), add + 1);
			} else {
				map.put(card.getSuit(), 1);
			}
		}

		for (Map.Entry<Card.Suit, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 5) {
				isFlush = true;
			}
		}
		// Start of evaluated logic
		if (isFlush) {
			int[] high = new int[1];
			high[0] = Collections.max(map.values());
			evaluated.addHand(Hand.FLUSH, high);
		}
		// end of evaluated logic

		return isFlush;
	}

	/**
	 * Check if given cards makes a straight. Any 5 consecutive cards of
	 * different suites. Remember that Ace is 14.
	 * 
	 * @param cards
	 *            cards to check
	 * @return true for straight, false if not straight
	 */
	public boolean straight(List<Card> cards) {
		Collections.sort(cards);
		evaluated = new Evaluated();

		// using a set to remove duplicates
		Set<Integer> cardSet = new HashSet<>();

		for (Card card : cards) {
			cardSet.add(card.getFaceValue());
		}

		Integer[] cardValuesArray = cardSet.toArray(new Integer[cardSet.size()]);

		int count = 0;
		for (int i = 0; i < cardValuesArray.length - 1; i++) {

			// special case for Ace since Ace got the value 14 in this
			// implementation
			if (cardValuesArray[i] == 2 && cardValuesArray[cardValuesArray.length - 1] == 14) {
				count = 2;
				continue;
			}

			if (cardValuesArray[i] + 1 == cardValuesArray[i + 1]) {
				count++;
				if (count == 4) {
					// Start of evaluated logic
					int[] high = new int[1];
					high[0] = cardValuesArray[i + 1];
					evaluated.addHand(Hand.STRAIGHT, high);
					// end of evaluated logic
					return true;
				}
			} else {
				count = 0;
			}
		}

		return false;
	}

	/**
	 * Check if given cards makes three of a kind. Any three cards of the same
	 * rank/faceValue.
	 * 
	 * @param cards
	 *            cards to check
	 * @return true for three of a kind, false if not
	 */
	public boolean threeOfAKind(List<Card> cards) {
		boolean three = false;
		Map<Integer, Integer> freq = cardFaceFrequency(cards);
		evaluated = new Evaluated();
		int threeOf = 0;
		for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
			if (entry.getValue() == 3) {
				threeOf = entry.getKey();
				three = true;
			}
		}
		// Start of evaluated logic
		if (three) {
			ArrayList<Integer> highest = new ArrayList<Integer>();
			for (Card c : cards) {
				if (c.getFaceValue() != threeOf) {
					highest.add(c.getFaceValue());
				}
			}
			Collections.sort(highest);
			int length = highest.size();
			int[] high = new int[3];
			high[0] = threeOf;
			high[1] = highest.get(length - 1);
			high[2] = highest.get(length - 2);
		}
		// end of evaluated logic
		return three;
	}

	/**
	 * Check if given cards makes two pairs. Any two cards of the same rank
	 * together with another two cards of the same rank.
	 * 
	 * @param cards
	 *            cards to check
	 * @return true for two pairs, false if not
	 */
	public boolean twoPair(List<Card> cards) {
		Map<Integer, Integer> freq = cardFaceFrequency(cards);
		evaluated = new Evaluated();
		ArrayList<Integer> pairValues = new ArrayList<>();
		int numPairs = 0;
		for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
			if (entry.getValue() == 2) {
				pairValues.add(entry.getKey());
				numPairs++;
			}
		}
		// Start of evaluated logic
		if (numPairs >= 2) {
			Collections.sort(pairValues);
			if (pairValues.size() == 3) {
				pairValues.remove(0);
			}
			int highestCard = 0;
			for (Card c : cards) {
				if (!pairValues.contains(c.getFaceValue()) && c.getFaceValue() > highestCard) {
					highestCard = 0;
				}
			}
			int[] high = new int[3];
			high[0] = pairValues.get(1);
			high[1] = pairValues.get(0);
			high[2] = highestCard;
			evaluated.addHand(Hand.TWOPAIRS, high);
		}
		// end of evaluated logic
		return numPairs >= 2;
	}

	/**
	 * Check if given cards makes a pair. Any two cards of the same rank.
	 * 
	 * @param cards
	 *            cards to check
	 * @return true if there is a pair, false if not
	 */
	public boolean onePair(List<Card> cards) {
		evaluated = new Evaluated();
		Map<Integer, Integer> freq = cardFaceFrequency(cards);
		boolean onePair = false;
		int pairValue = 0;
		for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
			if (entry.getValue() == 2) {
				pairValue = entry.getKey();
				onePair = true;
			}
		}
		// Start of evaluated logic
		if (onePair) {
			ArrayList<Integer> highestCards = new ArrayList<>();
			for (Card c : cards) {
				if (c.getFaceValue() != pairValue)
					highestCards.add(c.getFaceValue());
			}
			Collections.sort(highestCards);
			int length = highestCards.size();
			int[] high = new int[4];
			high[0] = pairValue;
			high[1] = highestCards.get(length - 1);
			high[2] = highestCards.get(length - 2);
			high[3] = highestCards.get(length - 3);
			evaluated.addHand(Hand.ONEPAIR, high);
		}
		// end of evaluated logic
		return onePair;
	}

	/**
	 * Will always return true. If no of the other checks pass then the hand is
	 * a highCard hand
	 * 
	 * @param cards
	 *            cards to check
	 * @return true, you should use the others tests first
	 */
	@SuppressWarnings("UnusedParameters")
	public boolean highCard(List<Card> cards) {
		evaluated = new Evaluated();
		Collections.sort(cards);
		int[] high = new int[5];
		int length = cards.size();
		for (int i = 0; i < 5; i++) {
			high[i] = cards.get(length - 1 - i).getFaceValue();
		}
		evaluated.addHand(Hand.ONEPAIR, high);
		return true;
	}

	/**
	 * How often a value shows up
	 * 
	 * @param cards
	 *            cards to check
	 * @return A map with the key as faceValue and value as number of times the
	 *         value shows up
	 */
	private Map<Integer, Integer> cardFaceFrequency(List<Card> cards) {
		HashMap<Integer, Integer> freq = new HashMap<>();

		for (Card c : cards) {
			int face = c.getFaceValue();
			int old_freq = freq.containsKey(face) ? freq.get(face) : 0;

			freq.put(face, old_freq + 1);
		}

		return freq;
	}

	/**
	 * check who wins of the players listed
	 * 
	 * @param table
	 * @param players
	 * @return Player
	 */
	public ArrayList<Player> getWinnerOfRound(Table table, ArrayList<Player> players) {
		HashMap<Player, Evaluated> playersAndEvaluated = new HashMap();
		HashMap<Evaluated, Player> evaluatedAndPlayers = new HashMap();
		for (Player p : players) {
			if (p == null){
				System.out.println("Player=null");
				continue;
			}
			List<Card> cards = table.getCommunityCards();
			cards.add(p.getCard1());
			cards.add(p.getCard2());
			Collections.sort(cards);
			Evaluated evaluated = this.evaluate(cards);
			playersAndEvaluated.put(p, evaluated);
			evaluatedAndPlayers.put(evaluated, p);
		}
		ArrayList<Player> winners = new ArrayList();
		ArrayList<Evaluated> evaluateds = new ArrayList();
		for (Evaluated ev : playersAndEvaluated.values())
			evaluateds.add(ev);
		Collections.sort(evaluateds);
		System.out.println("Length of evaluatedsList " + evaluateds.size());
		winners.add(evaluatedAndPlayers.get(evaluateds.get(0)));
		if (evaluateds.size()>=2){
		for (int i =1;i<evaluateds.size();i++){
			if (evaluateds.get(i).compareTo(evaluateds.get(i-1))==0){
				winners.add(evaluatedAndPlayers.get(evaluateds.get(i)));
			}else {
				System.out.println("Hands:"+evaluateds.get(i).getHand() + evaluateds.get(i-1).getHand());
			}
		}
		}
		return winners;
	}

	/**
	 * Reverse Enums for easier gtWinnerOfRound method
	 * 
	 * @param values
	 * @return Hand[] values
	 */
	public Hand[] reverse(Hand[] values) {
		if (values != null) {
			int length = values.length;
			Hand[] temp = new Hand[length];
			for (int i = 0; i < length; i++) {
				temp[i] = values[length - i - 1];
			}
			return temp;
		}
		return null;
	}

}
