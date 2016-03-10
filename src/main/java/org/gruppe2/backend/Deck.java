package org.gruppe2.backend;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for creating a standard deck of cards with 52 cards, 13 of each type.
 */
public class Deck {

    private ArrayList<Card> cards = new ArrayList<Card>();

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
        return cards.size();
    }

    /**
     * method used to draw a card from the deck. Card gets removed from the list
     * @return next card from deck
     */
    public Card drawCard() {
        if(cards.size() == 0) {
            return null;
        }

        return cards.remove(0);
    }

    /**
     * method to draw several cards from the deck. Throws an IllegalArgumentException if there are not enough cards in the deck
     * @param amount amount of cards to draw
     * @return ArrayList og cards
     */
    public ArrayList<Card> drawCards(int amount) {
       if(amount > cards.size()) {
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
        return cards;
    }
}
