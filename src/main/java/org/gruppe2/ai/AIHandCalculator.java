package org.gruppe2.ai;

import java.util.ArrayList;
import java.util.List;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Deck;
import org.gruppe2.game.old.GameClient;
import org.gruppe2.game.old.GameSession;
import org.gruppe2.game.old.Player;
import org.gruppe2.game.old.ShowdownEvaluator;
import org.gruppe2.game.old.ShowdownEvaluatorNew;
import org.gruppe2.game.old.Table;
import org.gruppe2.game.old.Card.Suit;

public class AIHandCalculator {
	
	/**
	 * Simulates x number of rounds with the current AI hand to return how many 
	 * of the rounds the AI will win with current hand
	 * @param table
	 * @param player
	 * @return double handStrength
	 */
	public static double getHandStrength(Table table, Player player){
		if (table==null)
			return 0;
		double handStrength=0;
		//int numActivePlayers = player.getClient().getSession().numActivePlayers();
		int numActivePlayers = 4;
		List<Card> cardsToRemove = table.getCommunityCards();
		cardsToRemove.add(player.getCard1());
		cardsToRemove.add(player.getCard2());
		int numberOfWins=0;
		ShowdownEvaluatorNew se = new ShowdownEvaluatorNew();
		
		for (int i = 0; i < 1000; i++){		
			AIMockTable mockTable = new AIMockTable();
			int numberOfCards=0;
			MockDeck d = new MockDeck();
			d.removeCards(cardsToRemove);
			for (Card c : table.getCommunityCards()){
				if (c==null){
					continue;
				}
				numberOfCards++;
				mockTable.setCard(new Card(c.getFaceValue(),c.getSuit()));
				//System.out.println("Table should have this card added: " + c);
			}
			int drawsLeft = 5-numberOfCards;
			for (int j = 0; j < drawsLeft; j++){
				mockTable.setCard(d.drawCard());
			}
			MockGameSession ms = new MockGameSession();
			ms.setTable(mockTable);
			Player player1 = new AIMockPlayer(player.getName());
			player1.setCards(player.getCard1(), player.getCard2());
			player1.getClient().setSession(ms);
			
			
			ArrayList<Player> newPlayers = new ArrayList<Player>();
			for (int j = 0; j<4;j++){
				Player p = new AIMockPlayer(j+"");
				p.setCards(d.drawCard(), d.drawCard());
				p.getClient().setSession(ms);
				newPlayers.add(p);
			}
			
			
			
			newPlayers.add(player1);
			ArrayList<Player> winners = se.getWinnerOfRound(newPlayers);
			for (Player p1 : winners){
				if (p1.getName().equals(player.getName())){
					numberOfWins++;
					continue;
				}
					
			}
			//System.out.println("Winners of round "+ i + ": " + winners.size() );
		}
		System.out.println(numberOfWins);
		numberOfWins*=1.0;
		handStrength=numberOfWins/1000.0;
		return handStrength;
	}
}

class AIMockPlayer extends Player {
	MockGameSession mocksession;
	AIMockGameClient mockclient = new AIMockGameClient();
	
    public AIMockPlayer(String name) {
        super(name, 10000, new AIMockGameClient());
    }
    
    @Override
    public GameClient getClient() {
    	return mockclient;
    }
    
    public GameSession getSession() {
    	return mocksession;
    }
}

class AIMockGameClient extends GameClient {
	GameSession mocksession;
	
	@Override
	public void setSession(GameSession session){
		mocksession = session;
	}
	
	@Override
	public GameSession getSession() {
    	return mocksession;
    }
}

class MockGameSession extends GameSession {
	AIMockTable mocktable;
	
	public void setTable(AIMockTable table){
		mocktable = table;
	}
	
	@Override
	public Table getTable() {
		return mocktable;
	}
}

class AIMockTable extends Table {
    Card c1 = null;
    Card c2 = null;
    Card c3 = null;
    Card c4 = null;
    Card c5 = null;

    public AIMockTable() {
        super();
    }
    
    public void setCard(Card c){
    	if (c1==null){
    		c1=c;
    		return;
    	}
    	if (c2==null){
    		c2=c;
    		return;
    	}
    	if (c3==null){
    		c3=c;
    		return;
    	}
    	if (c4==null){
    		c4=c;
    		return;
    	}
    	if (c5==null){
    		c5=c;
    		return;
    	}
    	
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
