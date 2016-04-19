package org.gruppe2.ui.javafx;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import org.gruppe2.game.old.GameBuilder;
import org.gruppe2.game.old.GameSession;
import org.gruppe2.game.old.Player;
import org.gruppe2.ui.Resources;

/**
 * This class will be split in several sub controllers, i.g Bottom Hbox with
 * buttons must be one class, etc..
 */
public class GameWindow extends BorderPane {

	private int width = PokerApplication.getWidth();
	private int height = PokerApplication.getHeight();
	
	List<Pane> playerInfoBoxes = new ArrayList<Pane>();
	GUIPlayer guiPlayer;
	Thread th;
	
	GameSession gameSession;
	CommunityCards communityCardsBox;
	public StatisticsMenu replayMenu;

	@FXML
	public Pane playerCards;
	@FXML
	private Table table;
	@FXML
	private ChoiceBar choiceBar;
	@FXML
	public Pot pot;

	public GameWindow() {
		Resources.loadFXML(this);
		communityCardsBox = table.communityCardsBox;
		PokerApplication.inGame = true;
		testGame();
	}

	

	/**
	 * Test game for watching game
	 */
	private void testGame() {
		addYourSelf();
		gameSession = new GameBuilder().ai(PokerApplication.numberOfBots).blinds(PokerApplication.big, PokerApplication.small)
				.startMoney(PokerApplication.bank).mainClient(guiPlayer).aiDifficulty(PokerApplication.diff).build();
		
		if(PokerApplication.replayMode == false){
			
			choiceBar.setEvents(guiPlayer);
			setUpPlayerBoxes();
			((ChatBox) table.getChildren().get(2))
			.setEventListeners((TextField) choiceBar.getChildren().get(0), gameSession.getPlayers().get(0));
			
		}
		else{
			for(String playerName : PokerApplication.replayPlayers){
				GUIPlayer guiPlayer = new GUIPlayer(this);
				guiPlayer.setName(playerName);
				
				if(!playerName.equals(PokerApplication.name)){
					gameSession.addPlayer(guiPlayer, PokerApplication.bank);
				}
			}
			setUpPlayerBoxes();
			//---> Start replay
		}
		th = new Thread(() -> gameSession.mainLoop());
		th.start();
		
		if(PokerApplication.replayMode == true){
			
		}
	}

	public void updateGameWindow(Player player) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				for (Pane playerInfoBox : playerInfoBoxes) {
					((PlayerInfoBox) playerInfoBox).updateInfoBox();
				}
				if(!PokerApplication.replayMode && replayMenu == null)
					choiceBar.updatePossibleBarsToClick(player);
				pot.updatePot(gameSession.getTable().getPot());

			}
		});
	}
	/**
	 * Create yourself
	 */
	private void addYourSelf() {
		guiPlayer = new GUIPlayer(this);
		guiPlayer.setName(PokerApplication.name);
	}
	/**
	 * Gets the game backend thread.
	 * @return
	 */
	public Thread getThread() {
		return th;
	}
	
	/**
	 * Show who won the round
	 * @param player
	 */
	public void displayRoundWon(Player player) {
		if (player.getName().equals(PokerApplication.name)) table.getChildren().add(new ConfettiOrMoney(200, true));
		ScreenText screenText = new ScreenText();
		screenText.setAnimationRoundWon(player);
		table.getChildren().add(screenText);
		screenText.setLayoutX(width/2);
		screenText.setLayoutY(height/2);

		if (gameSession.getActivePlayers().size() == 1) {
			table.chatBox.postMessage("\n" + player.getName() + " won");
		} else {
			for (Player p : gameSession.getActivePlayers()) {
				if (p == null)
					continue;

				table.chatBox.postMessage("\n" + p.getName() + " had " + p.getCard1().toString() + " and " + p.getCard2().toString());
				if (p == player) {
					table.chatBox.postMessage("(round winner)");
				}
			}
		}
	}
	/**
	 * Displays who won the entire game
	 * @param player
	 */
	public void displayGameWon(Player player) {
		ScreenText screenText = new ScreenText();
		table.getChildren().add(screenText);
		screenText.setLayoutX(width/2);
		screenText.setLayoutY(height/2);
		screenText.setAnimationGameWon(player);	
	}
	
	/**
	 * Sets up player boxes ingame
	 */

	public void setUpPlayerBoxes() {

		for (Player player : gameSession.getPlayers()) {
			
			PlayerInfoBox playerInfoBox = new PlayerInfoBox();
			playerInfoBoxes.add(playerInfoBox);
			playerInfoBox.setValues(player);
		}
		paintAllPlayers(playerInfoBoxes);
	}

	public void paintAllPlayers(List<Pane> playerInfoBoxes) {

		int numberOfPlayers = playerInfoBoxes.size();
		if (numberOfPlayers > 4)
			paintPlayerInfoBox(playerInfoBoxes.get(4), 0.34, 0.001);
		if (numberOfPlayers > 8)
			paintPlayerInfoBox(playerInfoBoxes.get(8), 0.45, 0.001);
		if (numberOfPlayers > 5)
			paintPlayerInfoBox(playerInfoBoxes.get(5), 0.58, 0.002);
		if (numberOfPlayers > 2)
			paintPlayerInfoBox(playerInfoBoxes.get(2), 0.19, 0.001);
		if (numberOfPlayers > 3)
			paintPlayerInfoBox(playerInfoBoxes.get(3), 0.69, 0.001);
		if (numberOfPlayers > 6)
			paintPlayerInfoBox(playerInfoBoxes.get(6), 0.05, 0.2);
		if (numberOfPlayers > 7)
			paintPlayerInfoBox(playerInfoBoxes.get(7), 0.8, 0.2);
		if (numberOfPlayers > 0)
			paintPlayerInfoBox(playerInfoBoxes.get(0), 0.03, 0.45);
		if (numberOfPlayers > 1)
			paintPlayerInfoBox(playerInfoBoxes.get(1), 0.82, 0.45);
	}

	public void paintPlayerInfoBox(Pane playerInfoBox, double x, double y) {

		playerInfoBox.setLayoutX(x * width);
		playerInfoBox.setLayoutY(y * height);

		playerInfoBox.layoutXProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(x));
		playerInfoBox.layoutYProperty().bind(
				PokerApplication.getRoot().heightProperty().multiply(y));

		getChildren().add(playerInfoBox);
	}
}
