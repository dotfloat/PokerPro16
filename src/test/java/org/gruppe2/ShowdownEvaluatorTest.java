package org.gruppe2;

import org.gruppe2.game.old.*;
import org.gruppe2.game.old.Card.Suit;
import org.gruppe2.game.old.ShowdownEvaluator.Hand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShowdownEvaluatorTest {

    ShowdownEvaluator evaluator;
    ArrayList<Card> cards;

    @Before
    public void setup() {
        evaluator = new ShowdownEvaluator();
        cards = new ArrayList<Card>();
    }

    @Test
    public void royalFlushShouldReturnTrueTest() {
        cards.add(new Card(10, Card.Suit.CLUBS));
        cards.add(new Card(11, Card.Suit.CLUBS));
        cards.add(new Card(7, Card.Suit.HEARTS));
        cards.add(new Card(12, Card.Suit.CLUBS));
        cards.add(new Card(13, Card.Suit.CLUBS));
        cards.add(new Card(14, Card.Suit.CLUBS)); // This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertTrue("Valid royal flush should be true", evaluator.royalFlush(cards));
        Evaluated ev = evaluator.evaluate(cards);
        Assert.assertTrue(ev.getHand() == Hand.ROYALFLUSH);
    }

    @Test
    public void invalidRoyalFlushShouldReturnFalseTest() {
        cards.add(new Card(9, Card.Suit.CLUBS));
        cards.add(new Card(11, Card.Suit.CLUBS));
        cards.add(new Card(12, Card.Suit.CLUBS));
        cards.add(new Card(13, Card.Suit.CLUBS));
        cards.add(new Card(14, Card.Suit.CLUBS));
        cards.add(new Card(7, Card.Suit.HEARTS));
        // This is Ace
        cards.add(new Card(10, Card.Suit.SPADES));
        assertFalse("Invalid royal flush should be false", evaluator.royalFlush(cards));
        Evaluated ev = evaluator.evaluate(cards);
        Assert.assertTrue(ev.getHand() != Hand.ROYALFLUSH);
    }

    @Test
    public void straightFlushShouldReturnTrueTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.CLUBS));
        cards.add(new Card(6, Card.Suit.CLUBS));
        cards.add(new Card(9, Card.Suit.HEARTS));
        // This is Ace
        cards.add(new Card(3, Card.Suit.SPADES));

        assertTrue("Valid straight flush should be true", evaluator.straightFlush(cards));
        Evaluated ev = evaluator.evaluate(cards);
        Assert.assertTrue(ev.getHand() == Hand.STRAIGHTFLUSH);
    }

    @Test
    public void invalidStraightFlushShouldReturnFalseTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(7, Card.Suit.HEARTS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.SPADES));
        cards.add(new Card(14, Card.Suit.DIAMONDS)); // This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertFalse("Invalid straight flush should be false", evaluator.straightFlush(cards));
        Evaluated ev = evaluator.evaluate(cards);
        Assert.assertTrue(ev.getHand() != Hand.STRAIGHTFLUSH);
    }

    @Test
    public void fourOfAKindShouldReturnTrueTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.HEARTS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.SPADES));
        cards.add(new Card(4, Card.Suit.DIAMONDS)); // This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertTrue("Four of a kind should be true", evaluator.fourOfAKind(cards));
        Evaluated ev = evaluator.evaluate(cards);
        Assert.assertTrue(ev.getHand() == Hand.FOUROFAKIND);
    }

    @Test
    public void invalidFourOfAKindShouldReturnFalseTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.HEARTS));
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.SPADES));
        cards.add(new Card(14, Card.Suit.DIAMONDS)); // This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertFalse("Invalid four of a kind should be false", evaluator.fourOfAKind(cards));
        Evaluated ev = evaluator.evaluate(cards);
        Assert.assertTrue(ev.getHand() != Hand.FOUROFAKIND);
    }

    @Test
    public void fullHouseShouldReturnTrueTest() {
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(3, Card.Suit.SPADES));
        cards.add(new Card(3, Card.Suit.HEARTS));
        cards.add(new Card(13, Card.Suit.CLUBS));
        cards.add(new Card(13, Card.Suit.DIAMONDS));
        cards.add(new Card(7, Card.Suit.CLUBS));
        cards.add(new Card(6, Card.Suit.DIAMONDS));

        assertTrue("Valid full house should be true", evaluator.fullHouse(cards));
        Evaluated ev = evaluator.evaluate(cards);
        Assert.assertTrue(ev.getHand() == Hand.FULLHOUSE);
    }

    @Test
    public void invalidFullHouseShouldReturnFalseTest() {
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.SPADES));
        cards.add(new Card(13, Card.Suit.HEARTS));
        cards.add(new Card(13, Card.Suit.CLUBS));
        cards.add(new Card(13, Card.Suit.DIAMONDS));
        cards.add(new Card(7, Card.Suit.CLUBS));
        cards.add(new Card(6, Card.Suit.DIAMONDS));

        assertFalse("Invalid full house should be true", evaluator.fullHouse(cards));
        Evaluated ev = evaluator.evaluate(cards);
        Assert.assertTrue(ev.getHand() != Hand.FULLHOUSE);
        Assert.assertTrue(evaluator.threeOfAKind(cards));
        Assert.assertTrue(ev.getHand() == Hand.THREEOFAKIND);


    }

    @Test
    public void flushShouldReturnTrueTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(7, Card.Suit.HEARTS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(12, Card.Suit.CLUBS));
        cards.add(new Card(6, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.DIAMONDS));

        assertTrue("Valid flush should be true", evaluator.flush(cards));
        Evaluated ev = evaluator.evaluate(cards);
//        System.out.println(ev.getHand());
        Assert.assertTrue(ev.getHand() == Hand.FLUSH);
    }

    @Test
    public void invalidFlushShouldReturnFalse() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.CLUBS));
        cards.add(new Card(6, Card.Suit.CLUBS));
        cards.add(new Card(7, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.SPADES));

        assertFalse("Invalid flush should be false", evaluator.flush(cards));
        Evaluated ev = evaluator.evaluate(cards);
        Assert.assertTrue(ev.getHand() != Hand.FLUSH);
    }

    @Test
    public void straightShouldReturnTrueTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(7, Card.Suit.HEARTS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.SPADES));
        cards.add(new Card(14, Card.Suit.DIAMONDS)); // This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertTrue("Valid straight should be true", evaluator.straight(cards));
        Evaluated ev = evaluator.evaluate(cards);
        Assert.assertTrue(ev.getHand() == Hand.STRAIGHT);
    }

    @Test
    public void invalidStraightShouldReturnFalseTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(7, Card.Suit.HEARTS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.SPADES));
        cards.add(new Card(14, Card.Suit.DIAMONDS)); // This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertFalse("Invalid straight should be false", evaluator.straight(cards));
        Evaluated ev = evaluator.evaluate(cards);
        Assert.assertTrue(ev.getHand() != Hand.STRAIGHT);
    }

    @Test
    public void threeOfAKindShouldReturnTrueTest() {
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.HEARTS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.SPADES));
        cards.add(new Card(14, Card.Suit.DIAMONDS)); // This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertTrue("Three of a kind should be true", evaluator.threeOfAKind(cards));
        Evaluated ev = evaluator.evaluate(cards);
        Assert.assertTrue(ev.getHand() == Hand.THREEOFAKIND);
    }

    @Test
    public void invalidThreeOfAKindShouldReturnFalseTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.HEARTS));
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.SPADES));
        cards.add(new Card(14, Card.Suit.DIAMONDS)); // This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertFalse("Invalid three of a kind should be false", evaluator.threeOfAKind(cards));
        Evaluated ev = evaluator.evaluate(cards);
        Assert.assertTrue(ev.getHand() != Hand.THREEOFAKIND);
    }

    @Test
    public void twoPairShouldReturnTrueTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.HEARTS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.SPADES));
        cards.add(new Card(14, Card.Suit.DIAMONDS)); // This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertTrue("Two pair should be true", evaluator.twoPair(cards));
        Evaluated ev = evaluator.evaluate(cards);
        Assert.assertTrue(ev.getHand() == Hand.TWOPAIRS);
    }

    @Test
    public void invalidTwoPairShouldReturnFalseTest() {
        cards.add(new Card(2, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.HEARTS));
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.SPADES));
        cards.add(new Card(14, Card.Suit.DIAMONDS)); // This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertFalse("Invalid two pair should be false", evaluator.twoPair(cards));
        Evaluated ev = evaluator.evaluate(cards);
        Assert.assertTrue(ev.getHand() != Hand.TWOPAIRS);
    }

    @Test
    public void onePairShouldReturnTrueTest() {
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.HEARTS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.SPADES));
        cards.add(new Card(12, Card.Suit.DIAMONDS)); // This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertTrue("One pair should be true", evaluator.onePair(cards));
        Evaluated ev = evaluator.evaluate(cards);
        Assert.assertTrue(ev.getHand() == Hand.ONEPAIR);
    }

    @Test
    public void invalidOnePairShouldReturnFalseTest() {
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(6, Card.Suit.HEARTS));
        cards.add(new Card(4, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.SPADES));
        cards.add(new Card(14, Card.Suit.DIAMONDS)); // This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertFalse("Invalid one pair should be true", evaluator.onePair(cards));
        Evaluated ev = evaluator.evaluate(cards);
        Assert.assertTrue(ev.getHand() != Hand.ONEPAIR);
    }

    @Test
    public void highCardShouldReturnTrueTest() {
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(8, Card.Suit.CLUBS));
        cards.add(new Card(4, Card.Suit.HEARTS));
        cards.add(new Card(7, Card.Suit.CLUBS));
        cards.add(new Card(5, Card.Suit.SPADES));
        cards.add(new Card(13, Card.Suit.DIAMONDS)); // This is Ace
        cards.add(new Card(2, Card.Suit.SPADES));

        assertTrue("High card should be true", evaluator.highCard(cards));
        Evaluated ev = evaluator.evaluate(cards);
        Assert.assertTrue(ev.getHand() == Hand.HIGHCARD);
    }

    @Test
    public void testRuntime() {
        cards.add(new Card(3, Card.Suit.CLUBS));
        cards.add(new Card(3, Card.Suit.SPADES));
        cards.add(new Card(3, Card.Suit.HEARTS));
        cards.add(new Card(13, Card.Suit.CLUBS));
        cards.add(new Card(13, Card.Suit.DIAMONDS));
        cards.add(new Card(7, Card.Suit.CLUBS));
        cards.add(new Card(6, Card.Suit.DIAMONDS));
        long msec = System.currentTimeMillis();
        Evaluated ev = evaluator.evaluate(cards);
        Assert.assertTrue(ev.getHand() == Hand.FULLHOUSE);
        long time = System.currentTimeMillis() - msec;
        System.out.println(time);
    }

    @Test
    public void testFullEvaluate() {
        MockTable t = new MockTable();
        t.setCards(new Card(3, Suit.CLUBS), new Card(8, Suit.DIAMONDS), new Card(9, Suit.SPADES),
                new Card(14, Suit.HEARTS), new Card(6, Suit.SPADES));
        MockPlayer p = new MockPlayer("Skal vinne");
        MockPlayer p2 = new MockPlayer("Skal tape");
        MockPlayer p3 = new MockPlayer("Skal ogsaa vinne");
        p3.setCards(new Card(2, Suit.HEARTS), new Card(2, Suit.SPADES));
        p.setCards(new Card(2, Suit.CLUBS), new Card(2, Suit.DIAMONDS));
        p2.setCards(new Card(4, Suit.DIAMONDS), new Card(12, Suit.DIAMONDS));
        ArrayList<Player> active = new ArrayList<Player>();
        active.add(p2);
        active.add(p);
        active.add(p3);
        ShowdownEvaluator se = new ShowdownEvaluator();
        ArrayList<Player> winners = se.getWinnerOfRound(t, active);
    }

    @Test
    public void testEvaluateWithRandomCards() {
        int numberOfWins = 0;
        for (int i = 0; i < 1000; i++) {
            Deck deck = new Deck();
            MockTable t = new MockTable();
            MockPlayer p1 = new MockPlayer("Player1");
            MockPlayer p2 = new MockPlayer("Player2");
            MockPlayer p3 = new MockPlayer("Player3");
            t.setCards(deck.drawCard(), deck.drawCard(), deck.drawCard(), deck.drawCard(), deck.drawCard());
            p1.setCards(new Card(2, Suit.CLUBS), new Card(2, Suit.DIAMONDS));
            p2.setCards(new Card(2, Suit.CLUBS), new Card(2, Suit.SPADES));
            p3.setCards(new Card(2, Suit.CLUBS), new Card(2, Suit.HEARTS));
            ArrayList<Player> active = new ArrayList<Player>();
            active.add(p1);
            active.add(p2);
            active.add(p3);
            ShowdownEvaluator se = new ShowdownEvaluator();
            ArrayList<Player> winners = se.getWinnerOfRound(t, active);
            if (winners.size() >= 2) {
            }
            if (winners.size() == 3) {
            }
//            System.out.println(winners.size());
            for (Player p : winners) {
                numberOfWins++;
            }
        }
//        System.out.println(numberOfWins);
    }

}

class MockPlayer extends Player {
	MockClient mockClient = new MockClient();
	MockSession mockSession = new MockSession();
	
    public MockPlayer(String name) {
        super(name, 10000, new GameClient());
    }
    
    public GameSession getSession() {
    	return mockSession;
    }
    
    public GameClient getClient() {
    	return mockClient;
    }
}

class MockClient extends GameClient{
	MockSession mockSession = new MockSession();
	
	public GameSession getSession() {
		return mockSession;
	}
}

class MockSession extends GameSession{
	MockTable mockTable = new MockTable();
	
	public Table getTable() {
		return mockTable;
	}
}

class MockTable extends Table {
    Card c1 = new Card(2, Suit.CLUBS);
    Card c2 = new Card(4, Suit.DIAMONDS);
    Card c3 = new Card(6, Suit.HEARTS);
    Card c4 = new Card(8, Suit.SPADES);
    Card c5 = new Card(10, Suit.CLUBS);

    public MockTable() {
        super();
    }

    public void setCards(Card c1, Card c2, Card c3, Card c4, Card c5) {
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.c5 = c5;
    }

    @Override
    public List<Card> getCommunityCards() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(c1);
        list.add(c2);
        list.add(c3);
        list.add(c4);
        list.add(c5);
        return list;
    }
}
