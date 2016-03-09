package org.gruppe2;

import org.gruppe2.backend.Card;
import org.junit.*;

import static org.junit.Assert.*;


public class CardTest {

   @Test
    public void cardSuitIsCorrectTest() {
       Card card = new Card(3, Card.Suit.HEARTS);
       assertEquals("New card is not of correct suit", Card.Suit.HEARTS, card.getSuit());
   }

    @Test
    public void cardFaceValueIsCorrectTest() {
        Card card = new Card(3, Card.Suit.HEARTS);
        assertEquals("New card does not have the correct faceValue", 3, card.getFaceValue());
    }

    @Test
    public void illegalFaceValueShouldThrowExceptionTest() {
        try {
            Card card = new Card(17, Card.Suit.HEARTS);
            assert(false);
        } catch (IllegalArgumentException e) {
            assert(true);
        }

    }
}
