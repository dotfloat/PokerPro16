package org.gruppe2;

import org.gruppe2.backend.Card;
import org.gruppe2.backend.Deck;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DeckTest {
    Deck deck;

    @Before
    public void setup() {
        deck = new Deck();
    }

    @Test
    public void deckIs52CardsTest() {
        assertEquals("Deck is not the right size", 52, deck.getSize());
    }

    @Test
    public void drawCardShouldDecrementSizeTest() {
        deck.drawCard();
        assertEquals("Deck is not the right size after drawing a card", 51, deck.getSize());
    }

    @Test
    public void drawingSeveralCardsShouldDecrementSizeTest() {
        int toDraw = 10;
        deck.drawCards(toDraw);
        assertEquals("Deck is not the right size after drawing several cards", 52-toDraw, deck.getSize());
    }

    @Test
    public void drawingTooManyCardsShouldThrowExceptionTest() {
        try {
            deck.drawCards(54);
            assert(false);
        } catch (IllegalArgumentException e) {
            assert(true);
        }
    }

    @Test
    public void deckShouldNotBeSortedAfterShuffleTest() {
        ArrayList<Card> temp = deck.getCards();
        deck.shuffle();
        assertFalse(temp.equals(deck.getCards()));
    }


}