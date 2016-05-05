package org.gruppe2.ui.javafx.menu;

import java.util.UUID;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import org.gruppe2.ui.UIResources;
import org.gruppe2.ui.javafx.PokerApplication;

/**
 * Created by Petter on 27/04/2016.
 */
public class LobbyTable extends StackPane {

	@FXML
	private ImageView tableImage;
	@FXML
	private StackPane table;
	@FXML
	private Label players;
	@FXML
	private Label name;
	Lobby lobby;

	public LobbyTable(String players, String name, Lobby lobby) {
		UIResources.loadFXML(this);
		this.players.setText(players);
		this.name.setText(name);
		this.lobby = lobby;
		setSize();
	}

	private void setSize() {
		double size = 0.13;
		tableImage.preserveRatioProperty().setValue(true);
		tableImage.fitWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(size));
	}
	@FXML
	private void joinTable(){
		if(lobby != null){
			lobby.requestJoinGame(UUID.fromString(name.getText()));
		}
	}
}
