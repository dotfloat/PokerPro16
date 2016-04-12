package org.gruppe2.game.calculation;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;


/**
 * Created by �smund on 12/04/2016.
 */
public class PairTest {

    @Test
   public void  canGetPairReturnsTrueWhenItShould(){
        ArrayList<Card> commCards = new ArrayList<Card>();
        commCards.add(new Card(2, Card.Suit.HEARTS));
        commCards.add(new Card(7, Card.Suit.SPADES));
        commCards.add(new Card(8, Card.Suit.CLUBS));

        Player p = new Player("test-guy", 50, null);
        p.setCards(new Card(4, Card.Suit.CLUBS), new Card(11, Card.Suit.SPADES));

        assertEquals(true, Pair.canGetPair(commCards, p));
    }


}