package org.gruppe2.ai;

import org.gruppe2.game.old.ShowdownEvaluator;
import org.gruppe2.game.old.ShowdownEvaluator.Hand;
import org.junit.Test;

public class BotTest {

    @Test
    public void testTest() {
        ShowdownEvaluator sh = new ShowdownEvaluator();
        for (Hand h : sh.reverse(Hand.values())) {
            System.out.println(h);
        }
    }

    // TODO: Fix test
//    @Test
//    public void dontFoldOnRoyalFlush() {
//        Table table = new PokerTableMock();
//        Bot bot = new Bot(100, table);
//        bot.giveCards(new Card(10, Card.Suit.CLUBS), new Card(11, Card.Suit.CLUBS));
//
//        assertNotEquals(Action.Type.FOLD, bot.onTurn().getType());
//    }
}

//class PokerTableMock extends Table {
//    public PokerTableMock() {
//        super(null, 0);
//    }
//
//    @Override
//    public ArrayList<Card> getCardOnTable() {
//        ArrayList<Card> cards = new ArrayList<>();
//        cards.add(new Card(7, Card.Suit.HEARTS));
//        cards.add(new Card(12, Card.Suit.CLUBS));
//        cards.add(new Card(13, Card.Suit.CLUBS));
//        cards.add(new Card(14, Card.Suit.CLUBS)); //This is Ace
//        cards.add(new Card(2, Card.Suit.SPADES));
//        return cards;
//    }
//}