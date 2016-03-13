package org.gruppe2.ai;

import org.gruppe2.backend.Action;
import org.gruppe2.backend.Card;
import org.gruppe2.backend.Deck;
import org.gruppe2.backend.PokerTable;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BotTest {
    @Test
    public void dontFoldOnRoyalFlush() {
        ArrayList<Card> cards = new ArrayList<>();
        PokerTable table = new PokerTable(new Deck(), 0);
        Bot bot = new Bot(100, table);
        cards.add(new Card(10, Card.Suit.CLUBS));
        cards.add(new Card(11, Card.Suit.CLUBS));
        cards.add(new Card(7, Card.Suit.HEARTS));
        cards.add(new Card(12, Card.Suit.CLUBS));
        cards.add(new Card(13, Card.Suit.CLUBS));
        cards.add(new Card(14, Card.Suit.CLUBS)); //This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertNotEquals(Action.Type.FOLD, bot.onTurn().getType());
    }
}