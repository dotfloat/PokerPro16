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
		
		if(isRoundNotFinished()){
			for(Player player : listOfPlayers){
				if(!player.hasFolded())
					playerRound(player);
				
			}
		}
	}
	
	private boolean isRoundNotFinished() {
		for(Player player : listOfPlayers){
			if(!player.hasFolded()){
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
//		System.out.println("finished");
		
	}

	private void round(Player player) {
//		System.out.println("middle");
		
	}

	private void startOfRound(Player player) {
		if(player.isSmallBlind()){
			player.pay(smallBlind);
		}
		else if(player.isBigBlind()){
			player.pay(bigBlind);
		}
		else{
			
		}
		
	}

	public void restart(int xMax, int yMax, int xMin, int yMin) {
		
		
	}

	

	public ArrayList<Card> getList() {
		return listOfCards;
	}
	
	private void testStart() {
		//Pre definition stage
		InitializeGame.setStartValues(this);
		
		//Set rest of players
		createBots(5);
		InitializeGame.setPlayersToTable(this, gui);
		//Initialize cards
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
			player.giveCards(deck.drawCard(), deck.drawCard());
		}
		
	}
	private void createBots(int i) {
		listOfPlayers.add(new Player("Dåsa",listOfPlayers.get(0).getChips(),true));
		listOfPlayers.add(new Player("Kåre",listOfPlayers.get(0).getChips(),true));
		listOfPlayers.add(new Player("Dangle",listOfPlayers.get(0).getChips(),true));
		listOfPlayers.add(new Player("MaqGruber",listOfPlayers.get(0).getChips(),true));
		listOfPlayers.add(new Player("SheMaleLion",listOfPlayers.get(0).getChips(),true));
		
	}
}
