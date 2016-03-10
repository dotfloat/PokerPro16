package org.gruppe2.frontend;

import java.util.ArrayList;
import java.util.Random;


public class TestSimulator {
	
	GUI gui;
	Random random = new Random();
	ArrayList<Card> listOfCards= new ArrayList<Card>();
	ArrayList<Player> listOfPlayers = new ArrayList<Player>();
	int smallBlind;
	int bigBlind;
	int currentPot;
	Deck deck;

	public TestSimulator(GUI gui) {
		this.gui = gui;
		deck = new Deck();
		deck.shuffle();
	}

	/**
	 * 1 round, that is until there is only 1 player left.
	 */
	public void playRound() {
		
		while(isRoundNotFinished()){
			for(Player player : listOfPlayers){
				if(player.isNotFolded)
					playerRound(player);
			}
		}
	}
	
	private boolean isRoundNotFinished() {
		for(Player player : listOfPlayers){
			if(player.isNotFolded){
				return true;
			}
		}
		return false;
	}

	public void playerRound(Player player){
		startOfRound(player);
		round(player);
		endOfRound(player);
	}

	
	
	private void endOfRound(Player player) {
		System.out.println("finished");
		
	}

	private void round(Player player) {
		System.out.println("middle");
		
	}

	private void startOfRound(Player player) {
		if(player.isSmallBlind){
			
		}
		else if(player.isBigBlind){
			
		}
		
	}

	public void restart(int xMax, int yMax, int xMin, int yMin) {
		
		
	}

	

	public ArrayList<Card> getList() {
		// TODO Auto-generated method stub
		return listOfCards;
	}
	
	private void testStart() {
		InitializeGame.setStartValues(this);
		InitializeGame.setPlayersToTable(this, gui);
		giveCardsToPlayers();
		gui.getMainFrame().showCardsOnHand(listOfPlayers);
		
	}

	
	public void startOfflineGame() {
		testStart();
	}



	public void startOnlineGame() {
		System.out.println("not yet implemented");
		
	}
	
	private void giveCardsToPlayers() {
		for(Player player : listOfPlayers){
			player.card1 = deck.drawCard();
			player.card2 = deck.drawCard();
		}
		
	}
}
