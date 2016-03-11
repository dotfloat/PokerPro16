package org.gruppe2;

import org.gruppe2.backend.Card;
import org.gruppe2.backend.ShowdownEvaluator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class ShowdownEvaluatorTest {

    ShowdownEvaluator evaluator;
    ArrayList<Card> cards;

    @Before
    public void setup() {
        evaluator = new ShowdownEvaluator();
        cards = new ArrayList<Card>();
    }

    @Test
    public void royalFlushShouldReturnTrueTest() {
        cards.add(new Card(10, Card.Suit.CLUBS));
        cards.add(new Card(11, Card.Suit.CLUBS));
        cards.add(new Card(7, Card.Suit.HEARTS));
        cards.add(new Card(12, Card.Suit.CLUBS));
        cards.add(new Card(13, Card.Suit.CLUBS));
        cards.add(new Card(14, Card.Suit.CLUBS)); //This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertTrue("Valid royal flush should be true",evaluator.royalFlush(cards));
    }

    @Test
    public void invalidRoyalFlushShouldReturnFalseTest() {
        cards.add(new Card(9, Card.Suit.CLUBS));
        cards.add(new Card(11, Card.Suit.CLUBS));
        cards.add(new Card(7, Card.Suit.HEARTS));
        cards.add(new Card(12, Card.Suit.CLUBS));
        cards.add(new Card(13, Card.Suit.CLUBS));
        cards.add(new Card(14, Card.Suit.CLUBS)); //This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertFalse("Invalid royal flush should be false",evaluator.royalFlush(cards));
    }

    @Test
    public void straightFlushShouldReturnTrueTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(7, Card.Suit.HEARTS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.CLUBS));
        cards.add(new Card(14, Card.Suit.CLUBS)); //This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertTrue("Valid straight flush should be true",evaluator.straightFlush(cards));
    }

    @Test
    public void invalidStraightFlushShouldReturnFalseTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(7, Card.Suit.HEARTS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.SPADES));
        cards.add(new Card(14, Card.Suit.DIAMONDS)); //This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertFalse("Invalid straight flush should be false",evaluator.straightFlush(cards));
    }

    @Test
    public void fourOfAKindShouldReturnTrueTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.HEARTS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.SPADES));
        cards.add(new Card(4, Card.Suit.DIAMONDS)); //This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertTrue("Four of a kind should be true",evaluator.fourOfAKind(cards));
    }

    @Test
    public void invalidFourOfAKindShouldReturnFalseTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.HEARTS));
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.SPADES));
        cards.add(new Card(14, Card.Suit.DIAMONDS)); //This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertFalse("Invalid four of a kind should be false",evaluator.fourOfAKind(cards));
    }

    @Test
    public void fullHouseShouldReturnTrueTest() {
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(3, Card.Suit.SPADES));
        cards.add(new Card(3, Card.Suit.HEARTS));
        cards.add(new Card(13, Card.Suit.CLUBS));
        cards.add(new Card(13, Card.Suit.DIAMONDS));
        cards.add(new Card(7, Card.Suit.CLUBS));
        cards.add(new Card(6, Card.Suit.DIAMONDS));

        assertTrue("Valid full house should be true",evaluator.fullHouse(cards));
    }

    @Test
    public void invalidFullHouseShouldReturnFalseTest() {
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.SPADES));
        cards.add(new Card(3, Card.Suit.HEARTS));
        cards.add(new Card(13, Card.Suit.CLUBS));
        cards.add(new Card(13, Card.Suit.DIAMONDS));
        cards.add(new Card(7, Card.Suit.CLUBS));
        cards.add(new Card(6, Card.Suit.DIAMONDS));

        assertFalse("Invalid full house should be true",evaluator.fullHouse(cards));
    }

    @Test
    public void flushShouldReturnTrueTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(7, Card.Suit.HEARTS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.CLUBS));
        cards.add(new Card(6, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.DIAMONDS));

        assertTrue("Valid flush should be true",evaluator.flush(cards));
    }

    @Test
    public void invalidFlushShouldReturnFalse() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.CLUBS));
        cards.add(new Card(6, Card.Suit.CLUBS));
        cards.add(new Card(7, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.SPADES));

        assertFalse("Invalid flush should be false",evaluator.flush(cards));
    }

    @Test
    public void straightShouldReturnTrueTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(7, Card.Suit.HEARTS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.SPADES));
        cards.add(new Card(14, Card.Suit.DIAMONDS)); //This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertTrue("Valid straight should be true",evaluator.straight(cards));
    }

    @Test
    public void invalidStraightShouldReturnFalseTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(7, Card.Suit.HEARTS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.SPADES));
        cards.add(new Card(14, Card.Suit.DIAMONDS)); //This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertFalse("Invalid straight should be false",evaluator.straight(cards));
    }

    @Test
    public void threeOfAKindShouldReturnTrueTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.HEARTS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.SPADES));
        cards.add(new Card(14, Card.Suit.DIAMONDS)); //This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertTrue("Three of a kind should be true",evaluator.threeOfAKind(cards));
    }

    @Test
    public void invalidThreeOfAKindShouldReturnFalseTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.HEARTS));
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.SPADES));
        cards.add(new Card(14, Card.Suit.DIAMONDS)); //This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertFalse("Invalid three of a kind should be false",evaluator.threeOfAKind(cards));
    }

    @Test
    public void twoPairShouldReturnTrueTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.HEARTS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.SPADES));
        cards.add(new Card(14, Card.Suit.DIAMONDS)); //This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertTrue("Two pair should be true",evaluator.twoPair(cards));
    }

    @Test
    public void invalidTwoPairShouldReturnFalseTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.HEARTS));
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.SPADES));
        cards.add(new Card(14, Card.Suit.DIAMONDS)); //This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertFalse("Invalid two pair should be false",evaluator.twoPair(cards));
    }

    @Test
    public void onePairShouldReturnTrueTest() {
            cards.add(new Card(3, Card.Suit.CLUBS));
            cards.add(new Card(8, Card.Suit.CLUBS));
            cards.add(new Card(4, Card.Suit.HEARTS));
            cards.add(new Card(4, Card.Suit.CLUBS));
            cards.add(new Card(5, Card.Suit.SPADES));
            cards.add(new Card(14, Card.Suit.DIAMONDS)); //This is Ace
            cards.add(new Card(2, Card.Suit.SPADES));

            assertTrue("One pair should be true",evaluator.onePair(cards));
    }

    @Test
    public void invalidOnePairShouldReturnFalseTest() {
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(6, Card.Suit.HEARTS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.SPADES));
        cards.add(new Card(14, Card.Suit.DIAMONDS)); //This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertFalse("Invalid one pair should be true",evaluator.onePair(cards));
    }

    @Test
    public void highCardShouldReturnTrueTest() {
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.HEARTS));
        cards.add(new Card(7, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.SPADES));
        cards.add(new Card(14, Card.Suit.DIAMONDS)); //This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertTrue("High card should be true",evaluator.highCard(cards));
    }

}
