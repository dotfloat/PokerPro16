package org.gruppe2.game.Calculation;

import junit.framework.TestCase;
import static org.junit.Assert.*;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Åsmund on 11/04/2016.
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
}