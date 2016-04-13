package org.gruppe2.ui.javafx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import org.gruppe2.game.old.Action;
import org.gruppe2.game.old.GameBuilder;
import org.gruppe2.game.old.Player;
import org.gruppe2.ui.Resources;

public class StatisticsMenu extends StackPane {
	private String name = "src/main/resources/testLogs/2016.104_09.52.txt";
	private String content;
	private boolean onlyGetStatistics = false;
	
	private ArrayList<String> playerNames;
	private ArrayList<Action> actions;
	private Player player;
	private int playerRoundsWon;
	
	@FXML private TextArea statistics;
	@FXML private TextField changeLog;
	
	public StatisticsMenu() {
		Resources.loadFXML(this);
		setStasticsTest(name);
		changeLog.setVisible(false);
	}

	private void setStasticsTest(String changedName) {
		name = changedName;
		content = "";
		
		try {
			content = new String(Files.readAllBytes(Paths.get(name)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		statistics.setText(content);
		statistics.setWrapText(true);
	}

	public void ok() {
		SceneController.setScene(new MainMenu());
	}

	public void cancel() {
		SceneController.setScene(new MainMenu());
	}
	
	public void getStatistics(){
		System.out.println("WHere are those GOD DAMN STATS! PRONTO!");
		if(this.player != null){
			getStatisticsForPlayer(player);
		}
	}	
	/**
	 * Entry point of replay
	 */
	public void replay(){
		System.out.println("Replay STATS! PRONTO!");
		
		
		PokerApplication.replayMode = true;
		SetUpReplay();
			
	}
	/**
	 * Changes the log to show
	 */
	public void changeLogFile(){
		if(!changeLog.getText().equals("") && !changeLog.getText().equals("Change Logg") ){
			name = changeLog.getText();
		}
	}
	private void SetUpReplay() {	
		System.out.println(content);
		String[] lines = content.split("\n");
		
		lineReader(lines);
	}

	/**
	 * Create a string of a log file.
	 * @param lines
	 */
	private void lineReader(String[] lines) {
		boolean valuesSet = false;
		boolean playersSet = false;
		
		playerNames = new ArrayList<String>();
		for(String line : lines){
			if(valuesSet){
				if(!playersSet){
					String[] words = line.split("\\s+");
					String player = words[3];
					
					if(player.equals("New")){
						playersSet = true;
						getPlayerActions(playerNames, lines);
					}
					else if(!playerNames.contains(player)){
						System.out.println("Added player");
						if(playerNames.size() == 0){
							PokerApplication.name = player;
						}
						playerNames.add(player);
					}
				}
			}
			if(!valuesSet){
				if(line.contains("Small Blind: ")){
					PokerApplication.small = Integer.valueOf(line.substring(line.indexOf("Small")).replaceAll("\\D+",""));
					System.out.println(PokerApplication.small);
				}
				else if(line.contains("Big Blind: ")){
					PokerApplication.big = Integer.valueOf(line.substring(line.indexOf("Big")).replaceAll("\\D+",""));
					System.out.println(PokerApplication.big);
				}
				else if(line.contains("New Turn!  ")){
					valuesSet = true;
				} 
			}
		}
	}

	
	/**
	 * Reads out action etc from log
	 * @param playerNames
	 * @param lines
	 */
	private void getPlayerActions(ArrayList<String> playerNames, String[] lines) {
		ArrayList<Action> actions = new ArrayList<Action>();
		for(String line : lines){
			for(String player : playerNames){
				if(line.contains(player)){
					if(!line.contains("won the pot!")){
						String[] words = line.split("\\s+");
						String action;
						if(words[5].equals(":")){
							action = words[6];
						}
						else
							action = words[5];
						
						System.out.println(action);
						Action actionObject = setSpecificAction(action);
						actions.add(actionObject);
					}
					break;
				}
			}	
		}
		if(onlyGetStatistics){
			getSpecificPlayerStatistics(playerNames, lines);
		}
		else{
			playReplayGame(playerNames,actions,lines);
		}
		
		
	}
	/**
	 * Sets the number of rounds won
	 * @param playerNames2
	 * @param lines
	 */
	private void getSpecificPlayerStatistics(ArrayList<String> playerNames2, String[] lines) {
		for(String line : lines){
			if(line.contains("won the pot!") && line.contains(player.getName().substring(0, player.getName().indexOf(" ")))){
				playerRoundsWon++;
			}
		}
	}
	/**
	 * Get action of player
	 * @param action
	 * @return
	 */
	private Action setSpecificAction(String action) {
		if(action.equals("Check"))
			return new Action.Check();
		else if(action.equals("Call"))
			return new Action.Call();
		else if(action.equals("Fold"))
			return new Action.Fold();
		else if(action.equals("Raise"))
			return new Action.Fold();
		else{
			System.out.println("Error in log");
			System.exit(1);
			return null;
		}	
	}
	/**
	 * The starter of the replay game
	 * @param playerNames
	 * @param actions
	 * @param lines
	 */
	private void playReplayGame(ArrayList<String> playerNames, ArrayList<Action> actions, String[] lines) {
		PokerApplication.replayPlayers = playerNames;
		
		PokerApplication.numberOfBots = 0;
		
		GameWindow gameWindow = new GameWindow();
		SceneController.setScene(gameWindow);	
		
	}
	
	/**
	 * Used to get only ingame player statistics from logs.
	 * @param player
	 * @return
	 */
	public ArrayList<String> getStatisticsForPlayer(Player player){
		this.player = player;
		setStasticsTest(name);
		ArrayList<String> stats = new ArrayList<String>();
		stats = addToStats(stats);
		
		return stats;
	}
	
	/**
	 * Used to get only ingame player statistics from logs.
	 * get the list of stats
	 * @param player
	 * @return
	 */
	private ArrayList<String> addToStats(ArrayList<String> stats) {
		stats.add(String.valueOf(playerRoundsWon)); //Index 1, won rounds
		
		return stats;
	}
}
