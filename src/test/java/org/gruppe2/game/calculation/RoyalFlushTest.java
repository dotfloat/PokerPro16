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
public class RoyalFlushTest {

    @Test
    public void canGetRoyalFlushTest() throws Exception {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(14, Card.Suit.HEARTS));
        cards.add(new Card(10, Card.Suit.SPADES));
        cards.add(new Card(11, Card.Suit.SPADES));

        Player p = new Player("TestPlayer", 0, null);
        p.setCards(new Card(12, Card.Suit.SPADES), new Card(2, Card.Suit.DIAMONDS));

        assertTrue(RoyalFlush.canGetRoyalFlush(cards, p));
    }

    @Test
    public void cantGetRoyalFlushTest() throws Exception {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(14, Card.Suit.HEARTS));
        cards.add(new Card(9, Card.Suit.SPADES));
        cards.add(new Card(11, Card.Suit.SPADES));

        Player p = new Player("TestPlayer", 0, null);
        p.setCards(new Card(12, Card.Suit.SPADES), new Card(2, Card.Suit.DIAMONDS));

        assertFalse(RoyalFlush.canGetRoyalFlush(cards, p));
    }
}