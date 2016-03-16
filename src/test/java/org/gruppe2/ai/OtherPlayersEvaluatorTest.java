package org.gruppe2.ai;

import java.util.ArrayList;
import java.util.Random;

import org.gruppe2.backend.Card;
import org.gruppe2.backend.Card.Suit;
import org.gruppe2.backend.Evaluated;
import org.gruppe2.backend.GameClient;
import org.gruppe2.backend.GameSession;
import org.gruppe2.backend.Player;
import org.gruppe2.backend.ShowdownEvaluator.Hand;
import org.gruppe2.backend.Table;
import org.junit.Before;
import org.junit.Test;

public class OtherPlayersEvaluatorTest {
	private OtherPlayersEvaluator evaluator;
	private GameSessionMock session;
	private Player bot;
	
	@Before
	public void generate() {
		evaluator = new OtherPlayersEvaluator();
		session = new GameSessionMock();
//		session.addPlayer("Mock", new GameClient(session));
//		session.addPlayer("Mock2", new GameClient(session));
		session.getPlayers().get(0).setCards(new Card(5, Suit.CLUBS), new Card(3, Suit.DIAMONDS));
		session.getTable().drawCommunityCards(1);
		bot = session.getPlayers().get(0);
	}
	
	@Test
	public void onePairIsCertainIfAlreadyOnTable() {
		ArrayList<Card> cards = new ArrayList<>();
		cards.add(new Card(2, Suit.CLUBS));
		cards.add(new Card(2, Suit.DIAMONDS));
		TableMock table = new TableMock();
		table.setCommunityCards(cards);
		session.setTable(table);
		
		evaluator.evaluate(session, bot);
	}
	
}

class GameSessionMock extends GameSession {
	TableMock table = new TableMock();
	
	public GameSessionMock() {
		super();
	}
	
	public void setTable(TableMock table) {
		this.table = table;
	}
	
	@Override
	public Table getTable() {
		return this.table;
	}
}

class TableMock extends Table {
	ArrayList<Card> mockCards = new ArrayList<>();
	
	public void setCommunityCards(ArrayList<Card> cards) {
		mockCards = cards;
	}
	
	@Override
	public ArrayList<Card> getCommunityCards() {
		return mockCards;
	}
}