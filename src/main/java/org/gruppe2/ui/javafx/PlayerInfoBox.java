package org.gruppe2.ui.javafx;

import java.awt.Label;

import org.gruppe2.ui.Resources;

import javafx.fxml.FXML;
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
		
	}
}
