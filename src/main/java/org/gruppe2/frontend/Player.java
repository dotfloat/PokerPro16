package org.gruppe2.frontend;

public class Player {
	String name;
	int currentChips;
	Card card1;
	Card card2;
	boolean isNotFolded = true;
	boolean isSmallBlind;
	boolean isBigBlind;
	
	
	public Player(String name, int currentChips){
		this.name = name;
		this.currentChips = currentChips;
	}
	
	public void setFold(){
		isNotFolded = false;
	}
	
	public void setNotFold(){
		isNotFolded = true;
	}
	
}
