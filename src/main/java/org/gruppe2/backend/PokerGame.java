package org.gruppe2.backend;

import org.gruppe2.frontend.GUI;

import java.util.ArrayList;

/**
 * class to play a pokergame
 */
public class PokerGame{

    GUI gui;
    int smallBlind;
    int bigBlind;

    private ArrayList<Player> players;
    
    // Skal ikke stå slik, er bare for foreløpig oversikt
    public ArrayList<Player> getPlayers() {
		return players;
	}
    //need pokerTable CEM PLS 
    
    
    
    public PokerGame(GUI gui, ArrayList<Player> players/*, PokerTable table*/){
        this.gui = gui;
        this.players = players;

        //need poker table and be able give players to board, and board to players
    }

    //and many more methods


    

	public static void main(String[] args) {
    	
        System.out.println("dette blir det beste spillet");
        
        GUI playerGUI = new GUI();
        
        ArrayList<Player> currentPlayers = new ArrayList<Player>();
        currentPlayers.add(new Player("First", 500));
        currentPlayers.add(new Player("Second", 500));
        
        PokerGame currentHand = new PokerGame(playerGUI, currentPlayers/*, playingTable*/);
        
        boolean gameDone = false;
        while (!gameDone) {
			
        	for (Player player : currentHand.getPlayers()) {
				player.doAction();				
			}
        	
        	System.out.println("The Ride never ends.");
        	
		}
        
    }
}
