package org.gruppe2.ai;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Player;
import org.junit.Test;
import org.gruppe2.game.old.Action;
import org.gruppe2.game.old.Card.Suit;

public class BotTest {

    @Test
    public void botShouldNotFoldWithGoodStartingHand() {
    	MockGameSession mockSession = new MockGameSession();
    	AIMockTable table = new AIMockTable();
//    	table.setCard(new Card(14, Suit.CLUBS));
//    	table.setCard(new Card(14, Suit.SPADES));
    	mockSession.setTable(table);
    	ArrayList<Player> myPlayers = new ArrayList<>();
    	
    	// Generate bot test subject:
    	AIMockPlayer player = new AIMockPlayer("MY TEST BOT");
    	myPlayers.add(player);
    	player.setCards(new Card(14, Suit.HEARTS), new Card(14,Suit.DIAMONDS));
        
        // Generate opponents:
        int amountOfOpponents = 4;
        for(int i = 0; i < amountOfOpponents; i++) {
        	AIMockPlayer p = new AIMockPlayer("Opponent " + (i+1));
        	myPlayers.add(p);
        	Random r = new Random();
        	p.setCards(new Card(r.nextInt(13)+2,Suit.CLUBS), new Card(r.nextInt(13)+2,Suit.HEARTS));
//        	AIMockClient mock = new AIMockClient(p, false);
//        	mock.setSession(mockSession);
        	p.getClient().setSession(mockSession);
        }
        
        for(Player p : myPlayers) {
        	p.getClient().setSession(mockSession);
        }
        
        for(Player p : myPlayers) {
        	if(p.getName().equals("MY TEST BOT"))
        		assertTrue(!p.getClient().onTurn(p).getClass().equals(new Action.Fold().getClass()));
//        	System.out.println(p.getClient().onTurn(p));
//        	System.out.println("Player " + p.getName() + " has cards:");
//        	System.out.println(p.getCard1());
//        	System.out.println(p.getCard2());
        }
    }
}