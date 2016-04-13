package org.gruppe2.game.calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by �smund on 11/04/2016.
 */
public class GeneralCalculations {

    public static ArrayList<Integer> recurringFaceValues(Collection<Card> communityCards, Player p) {
        ArrayList<Integer> recurringFaceValues = new ArrayList<Integer>();

        ArrayList<Card> allCards = new ArrayList<Card>(communityCards);

        HashMap<Integer, Integer> hashMapCards = new HashMap<Integer, Integer>();

        if (p.getCard1() != null && p.getCard2() != null) {
            allCards.add(p.getCard1());
            allCards.add(p.getCard2());

        }

        for (int i = 0; i < allCards.size(); i++) {
            int faceValue = allCards.get(i).getFaceValue();
            if (hashMapCards.containsKey(faceValue))
                hashMapCards.put(faceValue, hashMapCards.get(faceValue) + 1);

            else hashMapCards.put(faceValue, 1);

        }
        for(int i= 2; i < Card.ACE +1; i++){
            if(hashMapCards.containsKey(i)){
                if(hashMapCards.get(i) > 1)
                    for(int j = 0; j < hashMapCards.get(i); j++){
                        recurringFaceValues.add(i);
                    }
            }
        }
        return recurringFaceValues;
    }



    public static int amountOfSameFace(Collection<Card> communityCards, Player p){
        HashMap<Integer, Integer> amountCards = new HashMap<Integer, Integer>();
        int amountOfSameKind = 1;

        ArrayList<Card> allCards = new ArrayList<Card>(communityCards);

        if(p.getCard1() != null && p.getCard2() != null) {
            allCards.add(p.getCard1());
            allCards.add(p.getCard2());

        }


        for(int i = 0; i < allCards.size(); i++){
            int faceValue = allCards.get(i).getFaceValue();
            if(amountCards.containsKey(faceValue)){
                int amountOfCard = amountCards.get(faceValue) +1;
                amountCards.put(faceValue, amountOfCard);
                if(amountOfSameKind < amountOfCard)
                    amountOfSameKind = amountOfCard;
            }

            else
                amountCards.put(faceValue, 1);
        }
        return amountOfSameKind;
    }

    /**
     * A list over all types of hands to check probability and/or possibility.
     * List is sorted by hand rank
     * @return List over all hands possible
     */
    public static ArrayList<HandCalculation> getAllHandTypes(){
        ArrayList<HandCalculation> hands = new ArrayList<>();
        hands.add(new RoyalFlush());
        hands.add(new StraightFlush());
        hands.add(new FourOfAKind());
        hands.add(new FullHouse());
        hands.add(new Flush());
        hands.add(new Straight());
        hands.add(new ThreeOfAKind());
        hands.add(new TwoPairs());
        hands.add(new Pair());

        return hands;
    }

    /**
     * Looks at the cards and counts how many there are of each suit
     * @param allCards Cards
     * @return Map of suits as key and integer value of how may occurrences
     */
    public static HashMap<Card.Suit, Integer> numberOfEachSuit(Collection<Card> allCards){
        HashMap<Card.Suit, Integer> numTypes = new HashMap<>();
        numTypes.put(Card.Suit.CLUBS, 0);
        numTypes.put(Card.Suit.DIAMONDS, 0);
        numTypes.put(Card.Suit.SPADES, 0);
        numTypes.put(Card.Suit.HEARTS, 0);

        for(Card c : allCards)
            numTypes.put(c.getSuit(), numTypes.get(c.getSuit()) + 1);

        return numTypes;
    }

    /**
     * Finds the best hand for a specific player
     * (If there are no community cards, it will always return royal flush)
     * @param communityCards Cards on the table
     * @param p The player
     * @return The best hand a player can get.
     */
    public static HandType getBestHandForPlayer(Collection<Card> communityCards, Player p) {
        for (HandCalculation hand : getAllHandTypes())
            if (hand.canGetHand(communityCards, p))
                return hand.getType();
        return HandType.HIGHCARD;
    }

    /**
     * This is a function designed to reduce redundancy.
     * Makes a single list from the community cards and player cards.
     * @param communityCards Cards on the table
     * @param p Player
     * @return Combined list
     */
    public static ArrayList<Card> makeOneListOfCards(Collection<Card> communityCards, Player p){
        ArrayList<Card> allCards = new ArrayList<>(communityCards);
        allCards.add(p.getCard1());
        allCards.add(p.getCard2());

        return allCards;
    }
}
