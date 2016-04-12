package org.gruppe2.game.calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Mikal on 12.04.2016.
 */
public class StraightFlushTest {

    @Test
    public void cantGetStraightFlushTest() throws Exception {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(9, Card.Suit.CLUBS));
        cards.add(new Card(2, Card.Suit.DIAMONDS));
        cards.add(new Card(5, Card.Suit.DIAMONDS));
        cards.add(new Card(7, Card.Suit.SPADES));

        Player p = new Player("TestPlayer", 0, null);
        p.setCards(new Card(8, Card.Suit.DIAMONDS), new Card(6, Card.Suit.DIAMONDS));

        assertFalse(StraightFlush.canGetStraightFlush(cards, p));
    }

    @Test
    public void canGetStraightFlushTest() throws Exception {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(9, Card.Suit.CLUBS));
        cards.add(new Card(2, Card.Suit.DIAMONDS));
        cards.add(new Card(5, Card.Suit.DIAMONDS));
        cards.add(new Card(9, Card.Suit.DIAMONDS));

        Player p = new Player("TestPlayer", 0, null);
        p.setCards(new Card(8, Card.Suit.DIAMONDS), new Card(6, Card.Suit.DIAMONDS));

        assertTrue(StraightFlush.canGetStraightFlush(cards, p));
    }
}