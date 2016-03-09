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

	public TestSimulator(GUI gui) {
		this.gui = gui;

	}


	public void playRound() {
		for(Player player : listOfPlayers){
			playerRound(player);
		}
	}
	
	public void playerRound(Player player){
		startOfRound(player);
		round(player);
		endOfRound(player);
	}

	
	
	private void endOfRound(Player player) {
		System.out.println("start");
		
	}

	private void round(Player player) {
		System.out.println("middle");
		
	}

	private void startOfRound(Player player) {
		System.out.println("finished");
		
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
	}

	public void startOfflineGame() {
		testStart();
	}



	public void startOnlineGame() {
		System.out.println("not yet implemented");
		
	}
}
