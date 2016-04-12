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
	public int bigBlind = 50;
	public int smallBlind = 25;
	public int startValue = 500;
	ArrayList<Player> players = new ArrayList<Player>();
	List<Pane> playerInfoBoxes = new ArrayList<Pane>();
	GUIPlayer guiPlayer;
	Thread th;

	GameSession gameSession;
	CommunityCards communityCardsBox;
	Player yourself;

	@FXML
	public Pane playerCards;
	@FXML
	private Table table;
	@FXML
	private ChoiceBar choiceBar;
	

	public GameWindow() {
		Resources.loadFXML(this);

		((ChatBox) table.getChildren().get(2))
				.setEventListeners((TextField) choiceBar.getChildren().get(0));
		communityCardsBox = table.communityCardsBox;
		testGame();
	}

	

	/**
	 * This is for testing
	 */

	public void setUpPlayerBoxes() {

		for (Player player : gameSession.getPlayers()) {
			players.add(player);
			PlayerInfoBox playerInfoBox = new PlayerInfoBox();
			playerInfoBoxes.add(playerInfoBox);
			playerInfoBox.setValues(player);
		}
		System.out.println("Players size: " + players.size());
		paintAllPlayers(playerInfoBoxes);
	}

	public void paintAllPlayers(List<Pane> playerInfoBoxes) {

		int numberOfPlayers = playerInfoBoxes.size();
		if (numberOfPlayers > 3)
			paintPlayerInfoBox(playerInfoBoxes.get(3), 0.3, 0.001);
		if (numberOfPlayers > 4)
			paintPlayerInfoBox(playerInfoBoxes.get(4), 0.45, 0.001);
		if (numberOfPlayers > 5)
			paintPlayerInfoBox(playerInfoBoxes.get(5), 0.6, 0.002);
		if (numberOfPlayers > 2)
			paintPlayerInfoBox(playerInfoBoxes.get(2), 0.13, 0.001);
		if (numberOfPlayers > 6)
			paintPlayerInfoBox(playerInfoBoxes.get(6), 0.77, 0.001);
		if (numberOfPlayers > 1)
			paintPlayerInfoBox(playerInfoBoxes.get(1), 0.05, 0.2);
		if (numberOfPlayers > 7)
			paintPlayerInfoBox(playerInfoBoxes.get(7), 0.8, 0.2);
		if (numberOfPlayers > 0)
			paintPlayerInfoBox(playerInfoBoxes.get(0), 0.03, 0.45);
		if (numberOfPlayers > 8)
			paintPlayerInfoBox(playerInfoBoxes.get(8), 0.81, 0.45);
	}

	public void paintPlayerInfoBox(Pane playerInfoBox, double x, double y) {

		playerInfoBox.setLayoutX(x * width);
		playerInfoBox.setLayoutY(y * height);
		System.out.println(x + " " + y);
		playerInfoBox.maxWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.05));
		playerInfoBox.maxHeightProperty().bind(
				PokerApplication.getRoot().heightProperty().multiply(0.05));

		playerInfoBox.layoutXProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(x));
		playerInfoBox.layoutYProperty().bind(
				PokerApplication.getRoot().heightProperty().multiply(y));

		getChildren().add(playerInfoBox);
	}

	/**
	 * Test game for watching game
	 */
	private void testGame() {

		addYourSelf();
		gameSession = new GameBuilder().ai(8).blinds(bigBlind, smallBlind)
				.startMoney(startValue).mainClient(guiPlayer).build();

		setUpPlayerBoxes();
		// mainFrame.drawPot();

		th = new Thread(() -> gameSession.mainLoop());
		th.start();

	}

	public void updateGameWindow(Player player) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				for (Pane playerInfoBox : playerInfoBoxes) {
					((PlayerInfoBox) playerInfoBox).updateInfoBox();
				}
				choiceBar.updatePossibleBarsToClick(player);
			}
		});
	}

	private void addYourSelf() {
		guiPlayer = new GUIPlayer(this);
		yourself = new Player("Person", startValue, guiPlayer);
		players.add(yourself);
		choiceBar.setEvents(guiPlayer, yourself);
	}

	public Thread getThread() {
		return th;
	}
}
