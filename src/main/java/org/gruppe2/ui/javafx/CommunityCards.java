package org.gruppe2.ui.javafx;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import org.gruppe2.ui.Resources;

public class CommunityCards extends HBox {
	private int width = PokerApplication.getWidth();
	
	
	public CommunityCards(){
		Resources.loadFXML(this);
		setCommunityCards();
	}
	
	/**
	 * This is just test method for proof of conecpt, change this when backend is ready with playerCards.
	 */
	private void setCommunityCards() {
		
		for(int i = 0;i<5;i++){
			ImageView cardImage = new ImageView(new Image(("/images/cards/" + "c02" + ".png")));
			this.getChildren().add(cardImage);
			cardImage.setPreserveRatio(true);
            cardImage.setFitWidth(width * 0.05);
            cardImage.fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.05));
            
		}
		this.setAlignment(Pos.CENTER);
	}

}
