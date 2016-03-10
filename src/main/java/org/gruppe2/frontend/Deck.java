package org.gruppe2.frontend;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for creating a standard deck of cards with 52 cards, 13 of each type.
 */
public class Deck {
	
	
    private ArrayList<Card> cards = new ArrayList<Card>();
    private int nextCard = 0;

    public Deck() {
        for(Card.Suit suit : Card.Suit.values()) {
            for(int i = 2; i <= 14; i++) {
                cards.add(new Card(i,suit));
            }
        }
    }

    /**
     * @return size of the deck
     */
    public int getSize() {
        return cards.size() - nextCard;
    }

    /**
     * method used to draw a card from the deck. We add to the next card position to simulate cards getting removed from deck.
     * @return next card from deck
     */
    public Card drawCard() {
        if(nextCard > cards.size()) {
            return null;
        }

        return cards.get(nextCard++);
    }

    /**
     * method to draw several cards from the deck. Throws an IllegalArgumentException if there are not enough cards in the deck
     * @param amount amount of cards to draw
     * @return ArrayList og cards
     */
    public ArrayList<Card> drawCards(int amount) {
        if(nextCard+amount > cards.size()) {
            throw new IllegalArgumentException("Not enough cards in deck");
        }

        ArrayList<Card> drawnCards = new ArrayList<Card>();

        for(int i = 0; i < amount; i++) {
            drawnCards.add(drawCard());
        }

        return drawnCards;
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Get the cards that are still in the deck
     * @return cards still in deck
     */
    public ArrayList<Card> getCards() {
        ArrayList<Card> toReturn = new ArrayList<Card>();

        for(int i = nextCard; i < cards.size(); i++) {
            toReturn.add(cards.get(i));
        }

        return toReturn;
    }
}
