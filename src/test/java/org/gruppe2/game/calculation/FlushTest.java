package org.gruppe2.game.calculation;

import org.gruppe2.game.Calculation.Flush;
import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Mikal on 11.04.2016.
 */
public class FlushTest {

    @Test
    public void findsCorrectNumberOfSuitsTest() throws Exception {
        Collection<Card> cards = new ArrayList<>();
        cards.add(new Card(10, Card.Suit.HEARTS));
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(14, Card.Suit.SPADES));
        cards.add(new Card(8, Card.Suit.HEARTS));
        cards.add(new Card(9, Card.Suit.HEARTS));

        HashMap<Card.Suit, Integer> nt = Flush.numberOfEachType(cards);
        assertEquals(3, (int) nt.get(Card.Suit.HEARTS));
        assertEquals(1, (int) nt.get(Card.Suit.CLUBS));
        assertEquals(1, (int) nt.get(Card.Suit.SPADES));
        assertEquals(0, (int) nt.get(Card.Suit.DIAMONDS));
    }

    @Test
    public void canGetFlushTest() throws Exception {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(10, Card.Suit.DIAMONDS));
        cards.add(new Card(4, Card.Suit.DIAMONDS));
        cards.add(new Card(11, Card.Suit.CLUBS));

        Player p = new Player("TestPlayer", 0, null);
        p.setCards(new Card(3, Card.Suit.DIAMONDS), new Card(8, Card.Suit.SPADES));

        assertTrue(Flush.canGetFlush(cards, p));
    }

    @Test
    public void cantGetFlushTest() throws Exception {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(10, Card.Suit.DIAMONDS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(11, Card.Suit.CLUBS));

        Player p = new Player("TestPlayer", 0, null);
        p.setCards(new Card(3, Card.Suit.DIAMONDS), new Card(8, Card.Suit.SPADES));

        assertFalse(Flush.canGetFlush(cards, p));
    }
}