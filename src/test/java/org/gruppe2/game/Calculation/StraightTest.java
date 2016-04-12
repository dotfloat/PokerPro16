package org.gruppe2.game.calculation;

import org.gruppe2.game.Calculation.Straight;
import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Mikal on 11.04.2016.
 */
public class StraightTest {

    @Test
    public void cantGetStraightTest() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(8, Card.Suit.HEARTS));
        cards.add(new Card(6, Card.Suit.DIAMONDS));
        cards.add(new Card(14, Card.Suit.CLUBS));

        Player p = new Player("TestPlayer", 0, null);
        p.setCards(new Card(13, Card.Suit.DIAMONDS), new Card(14, Card.Suit.SPADES));

        assertFalse(Straight.canGetStraight(cards, p, false));
    }

    @Test
    public void canGetStraightTest() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(5, Card.Suit.HEARTS));
        cards.add(new Card(4, Card.Suit.DIAMONDS));
        cards.add(new Card(14, Card.Suit.CLUBS));

        Player p = new Player("TestPlayer", 0, null);
        p.setCards(new Card(13, Card.Suit.DIAMONDS), new Card(10, Card.Suit.SPADES));

        assertTrue(Straight.canGetStraight(cards, p, false));
    }
}