package org.gruppe2.game.calculation;

import org.gruppe2.game.Card;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Created by �smund on 11/04/2016.
 */
public class ThreeOfAKindTest{

    @Test
    public void canGetThreeOfAKindWorksWhenItShouldBeTrue(){
        ArrayList<Card> commCards = new ArrayList<Card>();
        commCards.add(new Card(4, Card.Suit.HEARTS));
        commCards.add(new Card(7, Card.Suit.SPADES));
        commCards.add(new Card(8, Card.Suit.CLUBS));
        commCards.add(new Card(2, Card.Suit.SPADES));



        Player p = new Player("test-guy", 50, null);
        p.setCards(new Card(2, Card.Suit.CLUBS), new Card(11, Card.Suit.SPADES));


        assertEquals(true, ThreeOfAKind.canGetThreeOfAKind(commCards, p));

    }

    @Test
    public void canGetThreeOfAKindWorksWhenItShouldBeFalse(){
        ArrayList<Card> commCards = new ArrayList<Card>();
        commCards.add(new Card(4, Card.Suit.HEARTS));
        commCards.add(new Card(7, Card.Suit.SPADES));
        commCards.add(new Card(8, Card.Suit.CLUBS));
        commCards.add(new Card(3, Card.Suit.SPADES));



        Player p = new Player("test-guy", 50, null);
        p.setCards(new Card(2, Card.Suit.CLUBS), new Card(11, Card.Suit.SPADES));


        assertEquals(false, ThreeOfAKind.canGetThreeOfAKind(commCards, p));
    }




}