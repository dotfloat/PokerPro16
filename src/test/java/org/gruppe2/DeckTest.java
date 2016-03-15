package org.gruppe2;

import org.gruppe2.backend.Card;
import org.gruppe2.backend.Deck;
import org.junit.*;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class DeckTest {
    Deck deck;

    @Before
    public void setup() {
        deck = new Deck();
    }

    @Test
    public void deckIs52CardsTest() {
        assertEquals("Deck is not the right size", 52, deck.getAvailableCards());
    }

    @Test
    public void drawCardShouldDecrementSizeTest() {
        deck.drawCard();
        assertEquals("Deck is not the right size after drawing a card", 51, deck.getAvailableCards());
    }

    @Test
    public void drawingSeveralCardsShouldDecrementSizeTest() {
        int toDraw = 10;
        deck.drawCards(toDraw);
        assertEquals("Deck is not the right size after drawing several cards", 52-toDraw, deck.getAvailableCards());
    }

    @Test(expected = RuntimeException.class)
    public void drawingTooManyCardsShouldThrowExceptionTest() {
        deck.drawCards(54);
    }

    @Test
    public void deckShouldNotHaveSameCardLocationsAfterShuffleTest() {
        ArrayList<Card> temp = new ArrayList<Card>(deck.getCards());
        deck.shuffle();
        boolean isSame = true;
        for(int i = 0; i < temp.size(); i++) {
            if(!temp.get(i).equals(deck.getCards().get(i))) {
                isSame = false;
                break;
            }
        }

        assertFalse(isSame);
    }


}
