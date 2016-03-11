package org.gruppe2.frontend;

import org.gruppe2.frontend.GUI;
import org.gruppe2.frontend.InitializeGame;

import java.util.ArrayList;

import javafx.application.Application;

/**
 * class to play a pokergame
 */
public class PokerGame implements Runnable{

    GUI gui;
    int startChips;
    int smallBlind;
    int bigBlind;
    
    private ArrayList<Player> players;
    private PokerTable table;

    boolean readyToPlay;
    
    
    public PokerGame(GUI gui){ //parameteren inn er gui
        this.gui = gui;
        this.readyToPlay = false;
        players = new ArrayList<Player>(); 
        table = new PokerTable(new Deck(),0);
    }

    

    public void startOfflineGame(){
    	setUpGame();
    }
    
    public void startOnlineGame() {
		System.out.println("not yet implemented");
	}


    private void setUpGame() {
    	InitializeGame.setStartValues(this);
        createBots(5);
        gui.getMainFrame().setPlayersToTable(this, gui);
        dealCardsToAll();
        gui.getMainFrame().showCardsOnHand(players);
        readyToPlay = true;
        table.setPlayers(players);
	}
    

	/**
	 * 1 round, that is until there is only 1 player left.
	 */
	public void playRound() {
		Player previousPlayer = players.get(players.size() -1);
		previousPlayer.doAction(Action.FINISHED);
		if(readyToPlay && isGameNotFinished()){
			for(Player player : players){
				if(!player.hasFolded()){
					
					while(previousPlayer.getChoice() == Action.WAITING){sleepWait();} //Wait
					
					playerRound(player);
					previousPlayer = player;
				}
			}
			resetPlayersForNextRound();
		}
	}
	
	private void resetPlayersForNextRound() {
		for( Player player : players){
			player.doAction(Action.WAITING);
		}
		
	}

	private boolean isGameNotFinished() {
		for(Player player : players){
			if(!player.hasFolded()){
				return true;
			}
		}
		return false;
	}

	public void playerRound(Player player){
		System.out.println("players turn: "+player.toString());
		startOfRound(player);
		gui.getMainFrame().reDraw();
		sleepWait();
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
			if(!player.isBot()){
				ChoicePopup.showChoices(this, player);
			}
			else{
				//DO BOT AI -->
				player.doAction(Action.CHECK);;
			}
		}
		
	}

	public void dealCardsToAll(){
        for(Player guy: players){
            guy.giveCards(table.deck.drawCard(), table.deck.drawCard());
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    
    public PokerTable getTable(){
    	return table;
    }

    private void createBots(int i) {
    	if(!(players.size() == 0)){
			players.add(new Player("Dåsa",players.get(0).getChips(),table,true));
			players.add(new Player("Kåre",players.get(0).getChips(),table,true));
			players.add(new Player("Dangle",players.get(0).getChips(),table,true));
			players.add(new Player("MaqGruber",players.get(0).getChips(),table,true));
			players.add(new Player("SheMaleLion",players.get(0).getChips(),table,true));
		}
    	else{
    		System.out.println("error, real player was not created");
    		System.exit(1);
    	}
	}
    
    public void addPlayer(String name){
        Player newPlayer = new Player(name, startChips, table);
        players.add(newPlayer);
      //table.addPlayer(newPlayer);
    }


    /**
     * Game loop
     */
	@Override
	public void run() {
		
		waitForGUISetup();
		
		gameLoop();

	}
	
	
	
	public void waitForGUISetup(){
		while(!readyToPlay){
			sleepWait();
		}
		System.out.println("ready");
	}
	public void gameLoop(){
		while(isGameNotFinished()){
			System.out.println("hei3");
			playRound();
			
		}
	}
	
	public void sleepWait(){
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    

}
