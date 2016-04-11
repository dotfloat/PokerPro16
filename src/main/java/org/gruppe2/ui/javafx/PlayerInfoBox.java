package org.gruppe2.ui.javafx;


import org.gruppe2.ui.Resources;
import org.gruppe2.game.old.Player;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Created by kjors on 07.04.2016.
 */
public class PlayerInfoBox extends Pane {
	Player player;
	
	@FXML private StackPane playerBoxStackPane;
    @FXML private Label playerName;
    @FXML private Label stack;
    @FXML private Label currentBet;
    @FXML private ImageView playerPicture;
//    Position pos;

	public PlayerInfoBox() {
		Resources.loadFXML(this);
//		setValues();
	}

	public void setValues(Player player) {
		this.player = player;
		playerName.setText(player.getName());
		currentBet.setText("0");
		stack.setText(String.valueOf(player.getBank()));
		
	}

	public void updateInfoBox() {
        System.out.println(player);
		if (player == null) {
            setVisible(false);
            return;
        }
        playerName.setText(player.getName());
        stack.setText("CHIPS: " + player.getBank());
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
	
}
