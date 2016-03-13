package org.gruppe2.ai;

import org.gruppe2.backend.Action;
import org.gruppe2.backend.Card;
import org.gruppe2.backend.Deck;
import org.gruppe2.backend.PokerTable;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BotTest {
    @Test
    public void dontFoldOnRoyalFlush() {
        PokerTable table = new PokerTableMock();
        Bot bot = new Bot(100, table);
        bot.giveCards(new Card(10, Card.Suit.CLUBS), new Card(11, Card.Suit.CLUBS));

        assertNotEquals(Action.Type.FOLD, bot.onTurn().getType());
    }
}

class PokerTableMock extends PokerTable {
    public PokerTableMock() {
        super(null, 0);
    }

    @Override
    public ArrayList<Card> getCardOnTable() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(7, Card.Suit.HEARTS));
        cards.add(new Card(12, Card.Suit.CLUBS));
        cards.add(new Card(13, Card.Suit.CLUBS));
        cards.add(new Card(14, Card.Suit.CLUBS)); //This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));
        return cards;
    }
}