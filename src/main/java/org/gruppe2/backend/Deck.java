package org.gruppe2.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for creating a standard deck of cards with 52 cards, 13 of each type.
 */
public class Deck {
    private ArrayList<Card> cards;
    private int cardsLeft;

    public Deck() {
        cards = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (int face = 2; face <= 14; face++) {
                cards.add(new Card(face, suit));
            }
        }

        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
        cardsLeft = 52;
    }

    /**
     * @return size of the deck
     */
    public int getAvailableCards() {
        return cardsLeft;
    }

    /**
     * method used to draw a card from the deck. Card gets removed from the list
     *
     * @return next card from deck
     */
    public Card drawCard() {
        if (cardsLeft <= 0) {
            throw new RuntimeException("Not enough cards in deck");
        }

        return cards.get(--cardsLeft);
    }

    /**
     * method to draw several cards from the deck. Throws an IllegalArgumentException if there are not enough cards in the deck
     *
     * @param amount amount of cards to draw
     * @return ArrayList og cards
     */
    public List<Card> drawCards(int amount) {
        ArrayList<Card> drawnCards = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            drawnCards.add(drawCard());
        }

        return drawnCards;
    }

    public List<Card> getCards() {
        return cards;
    }
}
