package org.gruppe2.ui.javafx;


import org.gruppe2.ui.Resources;
import org.gruppe2.ui.objects.Player;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Created by kjors on 07.04.2016.
 */
public class PlayerInfoBox extends Pane {
	
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

	private void setValues(Player player) {
		playerName.setText(player.getName());
		currentBet.setText("0");
		stack.setText(player.getStack());
	}
}
