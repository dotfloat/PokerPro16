package org.gruppe2.ai;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.gruppe2.game.Card;
import org.junit.Before;
import org.junit.Test;
import org.gruppe2.game.Card.Suit;

public class BotTest {
	
	MockGameSession mockSession = new MockGameSession();
	AIMockTable table = new AIMockTable();
	
	@Before
	public void settings() {
		mockSession.setTable(table);
	}
	
    @Test
    public void botShouldNotFoldWithGoodStartingHand() {
    	int amountOfOpponents = 4;
    	ArrayList<Player> myPlayers = new ArrayList<>();
    	
    	myPlayers.add(generateAIPlayer(new Card(14, Suit.HEARTS), new Card(14,Suit.DIAMONDS), "TESTSUBJECT"));
    	
        generateOpponentsAndAddToList(myPlayers, amountOfOpponents);
        
        for(Player p : myPlayers) {
        	p.getClient().setSession(mockSession);
        }
        
        for(Player p : myPlayers) {
        	Action action = p.getClient().onTurn(p);
        	if(action.getClass().equals(new Action.Raise(0).getClass())) {
//        		System.out.println("Bank: " + p.getBank());
//        		System.out.println("Betting: " + ((Action.Raise)action).getAmount());
        		for(Player players : myPlayers) {
        			((MockGameSession)players.getClient().getSession()).setHighestBet(((Action.Raise)action).getAmount());
        		}
        	}
        	
        	System.out.println("getHighestBet() = " + p.getClient().getSession().getHighestBet());
        	
        	if(p.getName().equals("TESTSUBJECT")) {
        		assertTrue(!p.getClient().onTurn(p).getClass().equals(new Action.Fold().getClass()));
        	}
        }
    }
//
//    @Test
//    public void botShouldFoldWithBadHandAgainstRaise() {
//    	int amountOfOpponents = 4;
//    	ArrayList<Player> myPlayers = new ArrayList<>();
//
//    	myPlayers.add(generateAIPlayer(new Card(14, Suit.CLUBS), new Card(14, Suit.SPADES),"RAISER"));
//    	generateOpponentsAndAddToList(myPlayers, amountOfOpponents);
//
//    	myPlayers.add(generateAIPlayer(new Card(2, Suit.CLUBS), new Card(5, Suit.SPADES), "TESTSUBJECT"));
//
//    	for(Player p : myPlayers) {
////    		System.out.println(p.getClient().onTurn(p));
//    		if(p.getName().equals("TESTSUBJECT")) {
//    			assertTrue(p.getClient().onTurn(p).getClass().equals(new Action.Fold().getClass()));
//    		}
//    	}
//    }
    
    private Player generateAIPlayer(Card c1, Card c2, String name) {
    	Player player = new AIMockPlayer(name);
    	player.setCards(c1, c2);
    	player.getClient().setSession(mockSession);
    	
    	return player;
    }
    
    private void generateOpponentsAndAddToList(List<Player> list, int opponents) {
    	 for(int i = 0; i < opponents; i++) {
         	AIMockPlayer p = new AIMockPlayer("Opponent " + (i+1));
         	list.add(p);
         	Random r = new Random();
         	p.setCards(new Card(r.nextInt(13)+2,Suit.CLUBS), new Card(r.nextInt(13)+2,Suit.HEARTS));
         	p.getClient().setSession(mockSession);
         }
    }
}