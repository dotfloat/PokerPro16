package org.gruppe2.ui.javafx;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import org.gruppe2.game.old.GameBuilder;
import org.gruppe2.game.old.GameSession;
import org.gruppe2.game.old.Player;

/**
 * This class will be split in several sub controllers, i.g Bottom Hbox with
 * buttons must be one class, etc..
 */
public class GameWindow implements Initializable {
	private int width = PokerApplication.getWidth();
	private int height = PokerApplication.getHeight();
	public int bigBlind = 50;
	public int smallBlind = 25;
	public int startValue = 500;
	ArrayList<Player> players = new ArrayList<Player>();
	List<Pane> playerInfoBoxes = new ArrayList<Pane>();
	GUIPlayer guiPlayer;
	GameSession gameSession;
	CommunityCards communityCardsBox;
	Player yourSelf;

	@FXML
	private BorderPane borderPane;
	@FXML
	private ImageView playerCard1;
	@FXML
	private ImageView playerCard2;
	@FXML
	private Table table;
	@FXML
	private ChoiceBar choiceBar;
	

	/**
	 * This is just test method for proof of conecpt, change this when backend
	 * is ready with playerCards.
	 */
	public void setPlayerCards() {
		playerCard1.setImage(new Image(("/images/cards/" + "c02" + ".png")));
		playerCard2.setImage(new Image(("/images/cards/" + "c03" + ".png")));
		playerCard1.setLayoutX(width * 0.80);
		playerCard1.setLayoutY(height * 0.77);
		playerCard2.setLayoutX(width * 0.88);
		playerCard2.setLayoutY(height * 0.77);

		playerCard1.setFitWidth(width * 0.12);
		playerCard1.setPreserveRatio(true);
		playerCard1.setSmooth(true);
		playerCard1.fitWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.12));
		playerCard2.setFitWidth(width * 0.12);
		playerCard2.setPreserveRatio(true);
		playerCard2.setSmooth(true);

		playerCard1.setRotate(350);
		playerCard2.setRotate(5);
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
		System.out.println("Players size: "+players.size());
		paintAllPlayers(playerInfoBoxes);
	}

	public void paintAllPlayers(List<Pane> playerInfoBoxes) {
		double xStep = width / 15;
		double yStep = height / 11;
		double y = 10;
		int numberOfPlayers = playerInfoBoxes.size();
		if (numberOfPlayers > 3)
			paintPlayerInfoBox(playerInfoBoxes.get(3), xStep * 2, y);
		if (numberOfPlayers > 4)
			paintPlayerInfoBox(playerInfoBoxes.get(4), xStep * 6.5, y);
		if (numberOfPlayers > 5)
			paintPlayerInfoBox(playerInfoBoxes.get(5), xStep * 11, y);
		if (numberOfPlayers > 2)
			paintPlayerInfoBox(playerInfoBoxes.get(2), xStep * 0.2, yStep * 2);
		if (numberOfPlayers > 6)
			paintPlayerInfoBox(playerInfoBoxes.get(6), xStep * 12.5, yStep * 2);
		if (numberOfPlayers > 1)
			paintPlayerInfoBox(playerInfoBoxes.get(1), xStep * 0.2, yStep * 4.2);
		if (numberOfPlayers > 7)
			paintPlayerInfoBox(playerInfoBoxes.get(7), xStep * 12.5,
					yStep * 4.2);
		if (numberOfPlayers > 0)
			paintPlayerInfoBox(playerInfoBoxes.get(0), xStep * 0.2, yStep * 6.4);
		if (numberOfPlayers > 8)
			paintPlayerInfoBox(playerInfoBoxes.get(8), xStep * 12.5,
					yStep * 6.4);
	}

	public void paintPlayerInfoBox(Pane playerInfoBox, double x, double y) {
		playerInfoBox.setLayoutX(x);
		playerInfoBox.setLayoutY(y);
		borderPane.getChildren().add(playerInfoBox);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		((ChatBox) table.getChildren().get(2))
				.setEventListeners((TextField) choiceBar.getChildren().get(0));
		communityCardsBox = table.communityCardsBox;
		testGame();

	}

	/**
	 * Test game for watching game
	 */
	private void testGame() {
		addYourSelf();
		gameSession = new GameBuilder().ai(7).blinds(bigBlind, smallBlind)
				.startMoney(startValue).mainClient(guiPlayer).build();

		
		setUpPlayerBoxes();
		// mainFrame.drawPot();
		
		Thread th = new Thread(() -> gameSession.mainLoop());
		th.start();
	}

	public void updateGameWindow() {
		 Platform.runLater(new Runnable() {
	            @Override
	            public void run() {
		for(Pane playerInfoBox : playerInfoBoxes){
			((PlayerInfoBox) playerInfoBox).updateInfoBox();
		}
		choiceBar.updatePossibleBarsToClick(yourSelf);
			}
		});
	}

	private void addYourSelf() {
		guiPlayer = new GUIPlayer(this);
		yourSelf = new Player("Person", startValue, guiPlayer);
		players.add(yourSelf);
		choiceBar.setEvents(guiPlayer,yourSelf);
	}

}
