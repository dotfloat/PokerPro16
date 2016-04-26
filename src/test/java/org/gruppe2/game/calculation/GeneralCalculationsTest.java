package org.gruppe2.game.calculation;

import static org.junit.Assert.*;

import org.gruppe2.game.Card;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by �smund on 11/04/2016.
 */
public class GeneralCalculationsTest{

    @Test
    public void amountOfSameFaceReturnsProperValues(){
        ArrayList<Card> commCards = new ArrayList<Card>();
        commCards.add(new Card(4, Card.Suit.HEARTS));
        commCards.add(new Card(7, Card.Suit.SPADES));
        commCards.add(new Card(8, Card.Suit.CLUBS));

        Player p = new Player("test-guy", 50, null);
        p.setCards(new Card(4, Card.Suit.CLUBS), new Card(4, Card.Suit.SPADES));

        assertEquals(3, GeneralCalculations.amountOfSameFace(commCards, p));
    }

    @Test
    public void recurrigFaceValuesReturnsProperList(){
        ArrayList<Card> commCards = new ArrayList<Card>();
        commCards.add(new Card(2, Card.Suit.HEARTS));
        commCards.add(new Card(4, Card.Suit.SPADES));
        commCards.add(new Card(8, Card.Suit.CLUBS));

        Player p = new Player("test-guy", 50, null);
        p.setCards(new Card(4, Card.Suit.CLUBS), new Card(5, Card.Suit.SPADES));

        ArrayList<Integer> recurringValues = GeneralCalculations.recurringFaceValues(commCards, p);

        assertEquals(4, recurringValues.get(0).intValue());
        assertEquals(2, recurringValues.size());

        commCards.add(new Card(5, Card.Suit.HEARTS));

        recurringValues = GeneralCalculations.recurringFaceValues(commCards,p);

        assertEquals(5, recurringValues.get(3).intValue());
        assertEquals(4, recurringValues.size());
    }
}