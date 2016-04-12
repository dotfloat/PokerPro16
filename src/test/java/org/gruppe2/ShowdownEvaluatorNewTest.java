package org.gruppe2;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Card.Suit;
import org.gruppe2.game.old.Deck;
import org.gruppe2.game.old.GameClient;
import org.gruppe2.game.old.HandCollector;
import org.gruppe2.game.old.Player;
import org.gruppe2.game.old.ShowdownEvaluator;
import org.gruppe2.game.old.ShowdownEvaluatorNew;
import org.gruppe2.game.old.Table;
import org.junit.Test;

public class ShowdownEvaluatorNewTest {

	@Test
	public void highCardTestShouldReturnHighestCard() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(13, Suit.DIAMONDS)); // King of Diamonds
		cards.add(new Card(2, Suit.CLUBS)); // 2 of Clubs
		cards.add(new Card(9, Suit.SPADES)); // 9 of Spades
		cards.add(new Card(8, Suit.HEARTS)); // 8 of Hearts
		cards.add(new Card(6, Suit.CLUBS)); // 6 of Clubs
		cards.add(new Card(4, Suit.DIAMONDS)); // 4 of Diamonds
		cards.add(new Card(2, Suit.HEARTS)); // 2 of Hearts
		cards.add(new Card(11, Suit.HEARTS)); // Knight of Hearts
		
		ShowdownEvaluatorNew showdown = new ShowdownEvaluatorNew();
		ArrayList<Card> result = showdown.getBestHighCard(cards);
//		printHands(result);
		
		assertTrue(result.get(0).equals(new Card(13, Suit.DIAMONDS)));
	}
	
	public void printHands(ArrayList<HandCollector> hands) {
		for(int i = 0; i < hands.size(); i++) {
			System.out.println("Hand " + i + ":");
			for(int j = 0; j < hands.get(i).getCards().size(); j++) {
				System.out.println(hands.get(i).getCards().get(j));
			}
		}
	}
	
	@Test
	public void testWinnersOfRound() {
		List<Player> players = new ArrayList<>();
		MockPlayer p1 = new MockPlayer("BOT 1");
		MockPlayer p2 = new MockPlayer("BOT 2");
		players.add(p1);
		players.add(p2);
		
		Card p1c1 = new Card(2, Suit.CLUBS);
		Card p1c2 = new Card(4, Suit.DIAMONDS);
		p1.setCards(p1c1, p1c2);
		
		Card p2c1 = new Card(2, Suit.HEARTS);
		Card p2c2 = new Card(4, Suit.SPADES);
		p2.setCards(p2c1, p2c2);
		
		/*
		System.out.println("Cards on table:");
		for(Card c : p1.getSession().getTable().getCommunityCards()) {
			System.out.println(c);
		}
		*/
		
		ShowdownEvaluatorNew se = new ShowdownEvaluatorNew();
		ArrayList<Player> winners = se.getWinnerOfRound(players);
		
		/*
		for(Player p : winners)
			System.out.println("Winner: " + p.getName());
		*/
	}
	
	@Test
	public void testWinnersOfRoundWithRandomCommunityCards() {
		List<Player> players = new ArrayList<>();
		
		int amountOfPlayers = 4;
		int N = 1000;
		
		for(int i = 1; i <= amountOfPlayers; i++) {
			MockPlayer p = new MockPlayer("BOT " + i);
			players.add(p);
			Card pc1 = new Card(2, Suit.CLUBS);
			Card pc2 = new Card(2, Suit.DIAMONDS);
			p.setCards(pc1, pc2);
		}
		
		for(int i = 0; i < N; i++) {
		
		Deck d = new Deck();
		Card c1 = d.drawCard();
		Card c2 = d.drawCard();
		Card c3 = d.drawCard();
		Card c4 = d.drawCard();
		Card c5 = d.drawCard();
		
		for(Player p : players) {
			MockTable mt = (MockTable) p.getClient().getSession().getTable();
			mt.setCards(c1, c2, c3, c4, c5);
		}
		
		ShowdownEvaluatorNew se = new ShowdownEvaluatorNew();
		ArrayList<Player> winners = se.getWinnerOfRound(players);
		
		assertTrue(winners.size() == amountOfPlayers);
		}
	}
	
	@Test
	public void testWinnersOfRoundComparedToDaniel() {
		ArrayList<Player> players = new ArrayList<>();
		
		int amountOfPlayers = 6;
		int N = 100;
		
		for(int i = 0; i < N; i++) {
		Deck d = new Deck();
		for(int j = 1; j <= amountOfPlayers; j++) {
			MockPlayer p = new MockPlayer("BOT " + j);
			players.add(p);
			p.setCards(d.drawCard(), d.drawCard());
		}
		
		Card c1 = d.drawCard();
		Card c2 = d.drawCard();
		Card c3 = d.drawCard();
		Card c4 = d.drawCard();
		Card c5 = d.drawCard();
		
		/*
		System.out.println(c1);
		System.out.println(c2);
		System.out.println(c3);
		System.out.println(c4);
		System.out.println(c5);
		*/
		
		for(Player p : players) {
			MockTable mt = (MockTable) p.getClient().getSession().getTable();
			mt.setCards(c1, c2, c3, c4, c5);
		}
		
		ShowdownEvaluatorNew se = new ShowdownEvaluatorNew();
		ArrayList<Player> winners = se.getWinnerOfRound(players);
		
		/*
		System.out.println("Player 0's community cards:");
		for(Card c : players.get(0).getClient().getSession().getTable().getCommunityCards())
			System.out.println(c);
		
		for(Player p : players)
			System.out.println(p.getName());
		*/
		org.gruppe2.game.old.ShowdownEvaluatorNew se2 = new org.gruppe2.game.old.ShowdownEvaluatorNew();
//		ArrayList<Player> stu = se2.getWinnerOfRound(players.get(0).getClient().getSession().getTable(), players);
		ArrayList<Player> stu = se2.getWinnerOfRound(players);
		
		/*
		System.out.println("START");
		
		for(Player p : players) {
			System.out.println(p.getName());
			System.out.println(p.getCard1());
			System.out.println(p.getCard2());
		}
		*/
		
		if(winners.size() != stu.size()) {
			/*
			System.out.println("Community cards:");
			for(Card c : players.get(0).getClient().getSession().getTable().getCommunityCards())
				System.out.println(c);
			
			System.out.println("Runar's winners:");
			*/
			for(Player p : winners) {
//				System.out.println(p.getName());
				ArrayList<Card> allCards = new ArrayList<>();
				allCards.addAll(p.getClient().getSession().getTable().getCommunityCards());
				allCards.add(p.getCard1());
				allCards.add(p.getCard2());
				ArrayList<HandCollector> hands = se.evaluateHands(allCards);
				/*
				for(HandCollector h : hands) {
					System.out.println("Rank: " + h.getRank());
				}
				System.out.println(p.getCard1());
				System.out.println(p.getCard2());
				 */
			}
			/*
			System.out.println("Daniel's winners:");
			for(Player p : stu) {
				System.out.println(p.getName());
				System.out.println(p.getCard1());
				System.out.println(p.getCard2());
			}
			System.out.println(winners.size() + " && " + stu.size());
			*/
		}
		
		assertTrue(winners.size() == stu.size());
		}
	}
	
	/*
	@Test
	public void printTest() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(2, Suit.DIAMONDS)); // 2 of Diamonds
		cards.add(new Card(2, Suit.CLUBS)); // 2 of Clubs
		cards.add(new Card(2, Suit.SPADES)); // 9 of Spades
		cards.add(new Card(13, Suit.HEARTS)); // King of Hearts
		cards.add(new Card(11, Suit.CLUBS)); // 6 of Clubs
		cards.add(new Card(13, Suit.DIAMONDS)); // King of Diamonds
		cards.add(new Card(11, Suit.HEARTS)); // 2 of Hearts
		cards.add(new Card(11, Suit.HEARTS)); // Knight of Hearts
		
		ShowdownEvaluatorNew showdown = new ShowdownEvaluatorNew();
		ArrayList<HandCollector> result = showdown.evaluateHands(cards);
//		ArrayList<Card> compare = new ArrayList<>(); // Dummy list to compare with.
//		compare.add(new Card(13, Suit.DIAMONDS));  // King of Diamonds
//		compare.add(new Card(13, Suit.HEARTS));  // King of Hearts
		
		for(HandCollector h : result) {
			System.out.println("Rank: " + h.getRank());
			for(Card c : h.getCards()) {
				System.out.println(c);
			}
		}
//		printHands(result);
		
//		assertTrue(result.containsAll(compare) && result.size() == compare.size());
	}
	*/
	
	@Test
	public void onePairTestShouldReturnHighestPair() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(2, Suit.DIAMONDS)); // 2 of Diamonds
		cards.add(new Card(2, Suit.CLUBS)); // 2 of Clubs
		cards.add(new Card(2, Suit.SPADES)); // 9 of Spades
		cards.add(new Card(13, Suit.HEARTS)); // King of Hearts
		cards.add(new Card(11, Suit.CLUBS)); // 6 of Clubs
		cards.add(new Card(13, Suit.DIAMONDS)); // King of Diamonds
		cards.add(new Card(11, Suit.HEARTS)); // 2 of Hearts
		cards.add(new Card(11, Suit.HEARTS)); // Knight of Hearts
		
		ShowdownEvaluatorNew showdown = new ShowdownEvaluatorNew();
		ArrayList<Card> result = showdown.getBestOnePair(cards);
		ArrayList<Card> compare = new ArrayList<>(); // Dummy list to compare with.
		compare.add(new Card(13, Suit.DIAMONDS));  // King of Diamonds
		compare.add(new Card(13, Suit.HEARTS));  // King of Hearts
		
//		printHands(result);
		
		assertTrue(result.containsAll(compare) && result.size() == compare.size());
	}

	@Test
	public void twoPairTestShouldReturnHighestTwoPairs() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(2, Suit.DIAMONDS)); // 2 of Diamonds
		cards.add(new Card(2, Suit.CLUBS)); // 2 of Clubs
		cards.add(new Card(9, Suit.SPADES)); // 9 of Spades
		cards.add(new Card(13, Suit.HEARTS)); // King of Hearts
		cards.add(new Card(9, Suit.CLUBS)); // 9 of Clubs
		cards.add(new Card(13, Suit.DIAMONDS)); // King of Diamonds
		cards.add(new Card(8, Suit.SPADES)); // 8 of Hearts
		cards.add(new Card(8, Suit.HEARTS)); // 8 of Hearts
		
		ShowdownEvaluatorNew showdown = new ShowdownEvaluatorNew();
		ArrayList<Card> result = showdown.getBestTwoPair(cards);
		ArrayList<Card> compare = new ArrayList<>(); // Dummy list to compare with.
		compare.add(new Card(13, Suit.DIAMONDS));  // King of Diamonds
		compare.add(new Card(13, Suit.HEARTS));  // King of Hearts
		compare.add(new Card(9, Suit.SPADES));  // King of Diamonds
		compare.add(new Card(9, Suit.CLUBS));  // King of Hearts
		
		assertTrue(result.containsAll(compare) && result.size() == compare.size());
	}
	
	@Test
	public void ThreeOfAKindTestShouldReturnHighestThreeOfAKind() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(2, Suit.DIAMONDS)); // 2 of Diamonds
		cards.add(new Card(2, Suit.CLUBS)); // 2 of Clubs
		cards.add(new Card(9, Suit.SPADES)); // 9 of Spades
		cards.add(new Card(13, Suit.HEARTS)); // King of Hearts
		cards.add(new Card(6, Suit.CLUBS)); // 6 of Clubs
		cards.add(new Card(13, Suit.DIAMONDS)); // King of Diamonds
		cards.add(new Card(2, Suit.HEARTS)); // 2 of Hearts
		cards.add(new Card(13, Suit.SPADES)); // King of Spades
		
		ShowdownEvaluatorNew showdown = new ShowdownEvaluatorNew();
		ArrayList<Card> result = showdown.getBestThreeOfAKind(cards);
		ArrayList<Card> compare = new ArrayList<>(); // Dummy list to compare with.
		compare.add(new Card(13, Suit.DIAMONDS));  // King of Diamonds
		compare.add(new Card(13, Suit.HEARTS));  // King of Hearts
		compare.add(new Card(13, Suit.SPADES)); // King of Spades
		
		assertTrue(result.containsAll(compare) && result.size() == compare.size());
	}
	
	@Test
	public void StraightTestShouldReturnHighestStraight() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(2, Suit.DIAMONDS)); // 2 of Diamonds
		cards.add(new Card(2, Suit.CLUBS)); // 2 of Clubs
		cards.add(new Card(3, Suit.SPADES)); // 3 of Spades
		cards.add(new Card(4, Suit.HEARTS)); // 4 of Hearts
		cards.add(new Card(5, Suit.CLUBS)); // 5 of Clubs
		cards.add(new Card(7, Suit.DIAMONDS)); // 7 of Diamonds
		cards.add(new Card(2, Suit.HEARTS)); // 2 of Hearts
		cards.add(new Card(13, Suit.SPADES)); // King of Spades
		cards.add(new Card(14, Suit.HEARTS)); // Ace of Hearts
		
		ShowdownEvaluatorNew showdown = new ShowdownEvaluatorNew();
		ArrayList<Card> result = showdown.getBestStraight(cards);
		ArrayList<Card> compare = new ArrayList<>(); // Dummy list to compare with.
		compare.add(new Card(2, Suit.HEARTS));  // 2 of Diamonds
		compare.add(new Card(3, Suit.SPADES));  // 3 of Hearts
		compare.add(new Card(4, Suit.HEARTS)); // 4 of Spades
		compare.add(new Card(5, Suit.CLUBS)); // 5 of Clubs
		compare.add(new Card(14, Suit.HEARTS)); // Ace of Hearts
		
		assertTrue(result.containsAll(compare) && result.size() == compare.size());
	}
	
	@Test
	public void FlushTestShouldReturnHighestFlush() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(2, Suit.DIAMONDS)); // 2 of Diamonds
		cards.add(new Card(2, Suit.HEARTS)); // 2 of Hearts
		cards.add(new Card(3, Suit.HEARTS)); // 3 of Hearts
		cards.add(new Card(4, Suit.HEARTS)); // 4 of Hearts
		cards.add(new Card(5, Suit.CLUBS)); // 5 of Clubs
		cards.add(new Card(7, Suit.HEARTS)); // 7 of Hearts
		cards.add(new Card(2, Suit.HEARTS)); // 2 of Hearts
		cards.add(new Card(13, Suit.HEARTS)); // King of Hearts
		cards.add(new Card(14, Suit.HEARTS)); // Ace of Hearts
		
		ShowdownEvaluatorNew showdown = new ShowdownEvaluatorNew();
		ArrayList<Card> result = showdown.getBestFlush(cards);
		ArrayList<Card> compare = new ArrayList<>(); // Dummy list to compare with.
		compare.add(new Card(3, Suit.HEARTS));  // 2 of Hearts
		compare.add(new Card(4, Suit.HEARTS));  // 3 of Hearts
		compare.add(new Card(7, Suit.HEARTS)); // 4 of Hearts
		compare.add(new Card(13, Suit.HEARTS)); // King of Hearts
		compare.add(new Card(14, Suit.HEARTS)); // Ace of Hearts
		
		assertTrue(result.containsAll(compare) && result.size() == compare.size());
	}
	
	@Test
	public void FullHouseTestShouldReturnHighestFullHouse() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(2, Suit.DIAMONDS)); // 2 of Diamonds
		cards.add(new Card(2, Suit.CLUBS)); // 2 of Clubs
		cards.add(new Card(9, Suit.SPADES)); // 9 of Spades
		cards.add(new Card(13, Suit.HEARTS)); // King of Hearts
		cards.add(new Card(9, Suit.CLUBS)); // 9 of Clubs
		cards.add(new Card(13, Suit.DIAMONDS)); // King of Diamonds
		cards.add(new Card(2, Suit.HEARTS)); // 2 of Hearts
		cards.add(new Card(13, Suit.SPADES)); // King of Spades
		cards.add(new Card(7, Suit.SPADES)); // 7 of Spades
		cards.add(new Card(7, Suit.HEARTS)); // 7 of Hearts
		
		ShowdownEvaluatorNew showdown = new ShowdownEvaluatorNew();
		ArrayList<Card> result = showdown.getBestFullHouse(cards);
		ArrayList<Card> compare = new ArrayList<>(); // Dummy list to compare with.
		compare.add(new Card(13, Suit.DIAMONDS));  // King of Diamonds
		compare.add(new Card(13, Suit.HEARTS));  // King of Hearts
		compare.add(new Card(13, Suit.SPADES));  // King of Hearts
		compare.add(new Card(9, Suit.SPADES)); // 9 of Spades
		compare.add(new Card(9, Suit.CLUBS)); // 9 of Spades
		
		assertTrue(result.containsAll(compare) && result.size() == compare.size());
	}
	
	@Test
	public void FourOfAKindTestShouldReturnHighestFourOfAKind() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(2, Suit.DIAMONDS)); // 2 of Diamonds
		cards.add(new Card(13, Suit.CLUBS)); // 2 of Clubs
		cards.add(new Card(9, Suit.SPADES)); // 9 of Spades
		cards.add(new Card(13, Suit.HEARTS)); // King of Hearts
		cards.add(new Card(6, Suit.CLUBS)); // 6 of Clubs
		cards.add(new Card(13, Suit.DIAMONDS)); // King of Diamonds
		cards.add(new Card(2, Suit.HEARTS)); // 2 of Hearts
		cards.add(new Card(13, Suit.SPADES)); // King of Spades
		
		ShowdownEvaluatorNew showdown = new ShowdownEvaluatorNew();
		ArrayList<Card> result = showdown.getBestFourOfAKind(cards);
		ArrayList<Card> compare = new ArrayList<>(); // Dummy list to compare with.
		compare.add(new Card(13, Suit.DIAMONDS));  // King of Diamonds
		compare.add(new Card(13, Suit.HEARTS));  // King of Hearts
		compare.add(new Card(13, Suit.SPADES)); // King of Spades
		compare.add(new Card(13, Suit.CLUBS)); // King og Clubs
		
		assertTrue(result.containsAll(compare) && result.size() == compare.size());
	}
	
	@Test
	public void StraightFlushTestShouldReturnHighestStraight() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(2, Suit.DIAMONDS)); // 2 of Diamonds
		cards.add(new Card(8, Suit.SPADES)); // 2 of Hearts
		cards.add(new Card(3, Suit.HEARTS)); // 3 of Hearts
		cards.add(new Card(4, Suit.HEARTS)); // 4 of Hearts
		cards.add(new Card(5, Suit.CLUBS)); // 5 of Clubs
		cards.add(new Card(7, Suit.SPADES)); // 7 of Hearts
		cards.add(new Card(5, Suit.HEARTS)); // 2 of Hearts
		cards.add(new Card(6, Suit.HEARTS)); // King of Hearts
		cards.add(new Card(7, Suit.HEARTS)); // Ace of Hearts
		
		ShowdownEvaluatorNew showdown = new ShowdownEvaluatorNew();
		ArrayList<Card> result = showdown.getBestStraightFlush(cards);
		ArrayList<Card> compare = new ArrayList<>(); // Dummy list to compare with.
		compare.add(new Card(3, Suit.HEARTS));  // 2 of Hearts
		compare.add(new Card(4, Suit.HEARTS));  // 3 of Hearts
		compare.add(new Card(5, Suit.HEARTS)); // 4 of Hearts
		compare.add(new Card(6, Suit.HEARTS)); // King of Hearts
		compare.add(new Card(7, Suit.HEARTS)); // Ace of Hearts
		
		assertTrue(result.containsAll(compare) && result.size() == compare.size());
	}
	
	@Test
	public void RoyalFlushTestShouldReturnHighestRoyalFlush() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(13, Suit.HEARTS)); // King of Hearts
		cards.add(new Card(6, Suit.DIAMONDS)); // 6 of Diamonds
		cards.add(new Card(9, Suit.HEARTS)); // 9 of Hearts
		cards.add(new Card(10, Suit.HEARTS)); // 10 of Hearts
		cards.add(new Card(6, Suit.CLUBS)); // 6 of Clubs
		cards.add(new Card(14, Suit.HEARTS)); // Ace of Hearts
		cards.add(new Card(11, Suit.HEARTS)); // Knight of Hearts
		cards.add(new Card(12, Suit.HEARTS)); // Queen of Hearts
		
		ShowdownEvaluatorNew showdown = new ShowdownEvaluatorNew();
		ArrayList<Card> result = showdown.getBestroyalFlush(cards);
		ArrayList<Card> compare = new ArrayList<>(); // Dummy list to compare with.
		compare.add(new Card(10, Suit.HEARTS)); // 10 of Hearts
		compare.add(new Card(11, Suit.HEARTS)); // Knight of Hearts
		compare.add(new Card(12, Suit.HEARTS)); // Queen of Hearts
		compare.add(new Card(13, Suit.HEARTS)); // King of Hearts
		compare.add(new Card(14, Suit.HEARTS)); // Ace of Hearts
		
		assertTrue(result.containsAll(compare) && result.size() == compare.size());
	}
	
}