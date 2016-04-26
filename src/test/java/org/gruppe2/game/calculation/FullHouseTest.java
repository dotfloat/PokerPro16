package org.gruppe2.game.calculation;

import org.gruppe2.game.Card;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;


/**
 * Created by Åsmund on 12/04/2016.
 */
public class FullHouseTest{
    @Test
    public void canGetFullHouseReturnsTrueWhenItShould(){
        ArrayList<Card> commCards = new ArrayList<Card>();
        Player p = new Player("test-guy", 50, null);

        p.setCards(new Card(4, Card.Suit.CLUBS), new Card(11, Card.Suit.SPADES));

        assertEquals(true, FullHouse.canGetFullHouse(commCards, p));
        commCards.add(new Card(4, Card.Suit.HEARTS));
        commCards.add(new Card(7, Card.Suit.SPADES));
        commCards.add(new Card(8, Card.Suit.CLUBS));

        assertEquals(true, FullHouse.canGetFullHouse(commCards, p));

        commCards.add(new Card(8, Card.Suit.SPADES));
        assertEquals(true, FullHouse.canGetFullHouse(commCards, p));

        commCards.add(new Card(5, Card.Suit.CLUBS));

        assertEquals(false, FullHouse.canGetFullHouse(commCards,p));

    }
}