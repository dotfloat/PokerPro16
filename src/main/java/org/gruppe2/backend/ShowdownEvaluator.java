package org.gruppe2.backend;

import java.util.*;

/**
 * A class for evaluating hands at showdown. Can be used to evaluate for specific hands, or by the evaluate method which returns the best hand form the cards.
 */
public class ShowdownEvaluator {

    /**
     * Possible hands sorted from lowest to highest value.
     *
     * This makes the comparison more idiomatic, ie:
     * <code>Hand.HIGHCARD < Hand.ROYALFLUSH</code>
     */
    public enum Hand {
        HIGHCARD,
        ONEPAIR,
        TWOPAIRS,
        THREEOFAKIND,
        STRAIGHT,
        FLUSH,
        FULLHOUSE,
        FOUROFAKIND,
        STRAIGHTFLUSH,
        ROYALFLUSH
    }

    /**
     * Method used to evaluate cards. Evaluated from best to worst. Returns the best hand.
     * @param cards cards to evaluate
     * @return enum Hand, best hand
     */

    public Hand evaluate(List<Card> cards) {
        if(royalFlush(cards))
            return Hand.ROYALFLUSH;
        else if(straightFlush(cards))
            return Hand.STRAIGHTFLUSH;
        else if(fourOfAKind(cards))
            return Hand.FOUROFAKIND;
        else if(fullHouse(cards))
            return Hand.FULLHOUSE;
        else if(flush(cards))
            return Hand.FLUSH;
        else if(straight(cards))
            return Hand.STRAIGHT;
        else if(threeOfAKind(cards))
            return Hand.THREEOFAKIND;
        else if(twoPair(cards))
            return Hand.TWOPAIRS;
        else if(onePair(cards))
            return Hand.ONEPAIR;
        else
            return Hand.HIGHCARD;
    }


    /**
     * Check if given cards makes a royal flush. A straight from a ten to an ace with all five cards of the same suit.
     * @param cards cards to check
     * @return true if it is a royal flush, false if not
     */
    public boolean royalFlush(List<Card> cards) {
        if(straight(cards) && flush(cards)) {
            Collections.sort(cards);

            //using a set to remove duplicates
            Set<Integer> cardSet = new HashSet<>();

            for(Card card : cards) {
                if(card.getFaceValue() >= 10)
                    cardSet.add(card.getFaceValue());
            }

            return cardSet.size() == 5;
        }
        else {
            return false;
        }
    }

    /**
     * Check if given cards makes a straight flush. Any straight with all five cards of the same suit.
     * @param cards cards to check
     * @return true if it is a straight flush, false if not
     */
    public boolean straightFlush(List<Card> cards) {
        return (flush(cards) && straight(cards));
    }

    /**
     * Check if given cards makes four of a kind. Any four cards of the same rank.
     * @param cards cards to check
     * @return true if four of a kind, false if not
     */
    public boolean fourOfAKind(List<Card> cards) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (Card card : cards) {
            if (map.containsKey(card.getFaceValue())) {
                int add = map.get(card.getFaceValue());
                map.replace(card.getFaceValue(), add + 1);
            } else {
                map.put(card.getFaceValue(), 1);
            }
        }

        boolean isFourOfAKind = false;
        for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if(entry.getValue() == 4) {
                isFourOfAKind = true;
            }
        }

        return isFourOfAKind;
    }

    /**
     * Check if given cards makes a full house. Any three cards of the same rank together with any two cards of the same rank.
     * @param cards cards to check
     * @return true for full house, false if not a full house
     */
    public boolean fullHouse(List<Card> cards) {
        return (threeOfAKind(cards) && twoPair(cards));
    }

    /**
     * Check if given cards makes a flush. Any 5 cards of the same suit.
     * @param cards list of cards to check
     * @return true for flush, false if not flush
     */
    public boolean flush(List<Card> cards) {
        boolean isFlush = false;
        HashMap<Card.Suit, Integer> map = new HashMap<>();

        for (Card card : cards) {
            if (map.containsKey(card.getSuit())) {
                int add = map.get(card.getSuit());
                map.replace(card.getSuit(), add + 1);
            } else {
                map.put(card.getSuit(), 1);
            }
        }

        for(Map.Entry<Card.Suit, Integer> entry : map.entrySet()) {
            if(entry.getValue() == 5) {
                isFlush = true;
            }
        }

        return isFlush;
    }

    /**
     * Check if given cards makes a straight. Any 5 consecutive cards of different suites. Remember that Ace is 14.
     * @param cards cards to check
     * @return true for straight, false if not straight
     */
    public boolean straight(List<Card> cards) {
        Collections.sort(cards);

        //using a set to remove duplicates
        Set<Integer> cardSet = new HashSet<>();

        for(Card card : cards) {
            cardSet.add(card.getFaceValue());
        }

        Integer[] cardValuesArray = cardSet.toArray(new Integer[cardSet.size()]);

        int count = 0;
        for(int i = 0; i < cardValuesArray.length-1; i++) {

            //special case for Ace since Ace got the value 14 in this implementation
            if(cardValuesArray[i] == 2 && cardValuesArray[cardValuesArray.length-1] == 14) {
                count = 2;
                continue;
            }

            if(cardValuesArray[i]+1 == cardValuesArray[i+1]) {
                count++;
                if(count == 4)
                    return true;
            }
            else {
                count = 0;
            }
        }

        return false;
    }

    /**
     * Check if given cards makes three of a kind. Any three cards of the same rank/faceValue.
     * @param cards cards to check
     * @return true for three of a kind, false if not
     */
    public boolean threeOfAKind(List<Card> cards) {
        Map<Integer, Integer> freq = cardFaceFrequency(cards);

        for(Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            if(entry.getValue() >= 3) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check if given cards makes two pairs. Any two cards of the same rank together with another two cards of the same rank.
     * @param cards cards to check
     * @return true for two pairs, false if not
     */
    public boolean twoPair(List<Card> cards) {
        Map<Integer, Integer> freq = cardFaceFrequency(cards);

        int numPairs = 0;
        for(Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            if(entry.getValue() >= 2) {
                numPairs++;
            }
        }

        return numPairs == 2;
    }

    /**
     * Check if given cards makes a pair. Any two cards of the same rank.
     * @param cards cards to check
     * @return true if there is a pair, false if not
     */
    public boolean onePair(List<Card> cards) {
        Map<Integer, Integer> freq = cardFaceFrequency(cards);

        for(Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            if(entry.getValue() == 2) {
                return true;
            }
        }

        return false;
    }

    /**
     * Will always return true. If no of the other checks pass then the hand is a highCard hand
     * @param cards cards to check
     * @return true, you should use the others tests first
     */
    @SuppressWarnings("UnusedParameters")
    public boolean highCard(List<Card> cards) {
        return true;
    }

    /**
     * How often a value shows up
     * @param cards cards to check
     * @return A map with the key as faceValue and value as number of times the value shows up
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
}
