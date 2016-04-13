package org.gruppe2.ui.javafx;


import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import org.gruppe2.game.old.Player;
import org.gruppe2.ui.Resources;

/**
 * Created by kjors on 07.04.2016.
 */
public class PlayerInfoBox extends GridPane {
	Player player;

    @FXML private Label playerName;
    @FXML private Label stack;
    @FXML private Label currentBet;
    @FXML private ImageView playerPicture;
//    Position pos;

	public PlayerInfoBox() {
		Resources.loadFXML(this);
		playerPicture.fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.06));
		playerName.fontProperty().bind(ChoiceBar.fontTracking);
		stack.fontProperty().bind(ChoiceBar.fontTracking);
		currentBet.fontProperty().bind(ChoiceBar.fontTracking);

	}

	public void setValues(Player player) {
		this.player = player;
		playerName.setText(player.getName());
		currentBet.setText("0");
		stack.setText(String.valueOf(player.getBank()));
		
	}

	public void updateInfoBox() {
		if (player == null) {
            setVisible(false);
            return;
        }
        playerName.setText(player.getName());
        stack.setText("$"+player.getBank());
        currentBet.setText("BET: " + player.getBet());
        updatePicture();
    }
	private void updatePicture() {
        if (player.getClient().getSession().playerHasFolded(player))
        	playerPicture.setImage(new Image
                    (getClass().getResourceAsStream("/images/avatars/defaultFolded.png")));
        else
        	playerPicture.setImage(new Image
                    (getClass().getResourceAsStream("/images/avatars/default.png")));

    }
	 public void setActive() {
	        getStyleClass().clear();
	        getStyleClass().add("paneActive");
	}
	
	public void setInActive() {
	    getStyleClass().clear();
	    getStyleClass().add("pane");
	}
	
	public Player getPlayer(){
		return player;
	}
}
