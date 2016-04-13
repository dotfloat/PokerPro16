package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import org.gruppe2.game.old.GameSession;
import org.gruppe2.game.old.Player;
import org.gruppe2.ui.Resources;

public class PlayerCards extends HBox {
	private int width = PokerApplication.getWidth();
	private boolean firstSet = true;	
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
		Player player = gameSession.getPlayers().get(0);
		setPaneStyle();
		
		playerCard1.setImage(new Image(("/images/cards/"
				+ communityCardsBox.getCardName(player.getCard1()) + ".png")));
		playerCard2.setImage(new Image(("/images/cards/"
				+ communityCardsBox.getCardName(player.getCard2()) + ".png")));
		
		checkFirstSet();
	}
	
	private void setPaneStyle(){
		this.layoutXProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.80));
		this.layoutYProperty().bind(
				PokerApplication.getRoot().heightProperty().multiply(0.78));

	}
	/**
	 * First time needs to set layout
	 */
	private void checkFirstSet(){
		if(firstSet){
			playerCard1.fitWidthProperty().bind(
					PokerApplication.getRoot().widthProperty().multiply(0.12));
			playerCard1.setPreserveRatio(true);
			playerCard1.setSmooth(true);
			
			
			playerCard2.fitWidthProperty().bind(
					PokerApplication.getRoot().widthProperty().multiply(0.12));
			playerCard2.setPreserveRatio(true);
			playerCard2.setSmooth(true);
			
			this.spacingProperty().bind(PokerApplication.getRoot().widthProperty().multiply(-0.07));
			
			playerCard1.setRotate(350);
			playerCard2.setRotate(10);
			
			firstSet = false;
		}
	}
}
