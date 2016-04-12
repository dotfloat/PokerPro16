package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import org.gruppe2.game.old.GameSession;
import org.gruppe2.game.old.Player;
import org.gruppe2.ui.Resources;

public class PlayerCards extends Pane {
	private int width = PokerApplication.getWidth();
	private int height = PokerApplication.getHeight();
	
	@FXML ImageView playerCard1;
	@FXML ImageView playerCard2;
	
	public PlayerCards() {
		Resources.loadFXML(this);
	}
	
	/**
	 * This is just test method for proof of conecpt, change this when backend
	 * is ready with playerCards.
	 */
	public void setPlayerCards(GameSession gameSession,CommunityCards communityCardsBox) {
		System.out.println("hello");
		Player player = gameSession.getPlayers().get(0);
		playerCard1.setImage(new Image(("/images/cards/"
				+ communityCardsBox.getCardName(player.getCard1()) + ".png")));
		playerCard2.setImage(new Image(("/images/cards/"
				+ communityCardsBox.getCardName(player.getCard2()) + ".png")));
		playerCard1.setLayoutX(width * 0.80);
		playerCard1.setLayoutY(height * 0.77);
		playerCard2.setLayoutX(width * 0.88);
		playerCard2.setLayoutY(height * 0.77);
		playerCard1.layoutXProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.80));
		playerCard1.layoutYProperty().bind(
				PokerApplication.getRoot().heightProperty().multiply(0.77));
		playerCard2.layoutXProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.88));
		playerCard2.layoutYProperty().bind(
				PokerApplication.getRoot().heightProperty().multiply(0.77));
		playerCard1.setFitWidth(width * 0.12);
		playerCard1.setPreserveRatio(true);
		playerCard1.setSmooth(true);
		playerCard1.fitWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.12));
		playerCard2.setFitWidth(width * 0.12);
		playerCard2.setPreserveRatio(true);
		playerCard2.setSmooth(true);
		playerCard2.fitWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.12));

		playerCard1.setRotate(350);
		playerCard2.setRotate(5);
		
		this.maxWidthProperty().bind((playerCard1.fitWidthProperty().multiply(2)));
		this.maxHeightProperty().bind((playerCard1.fitHeightProperty().multiply(2)));
		this.setLayoutX(width * 0.80);
		this.setLayoutY(height * 0.77);
	}
}
