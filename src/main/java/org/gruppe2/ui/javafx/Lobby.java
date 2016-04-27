package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;

import org.gruppe2.game.event.NetworkClientEvent;
import org.gruppe2.game.session.Handler;
import org.gruppe2.network.NetworkClient;
import org.gruppe2.network.NetworkTester;
import org.gruppe2.ui.Resources;

/**
 * Created by Petter on 11/04/2016.
 */

public class Lobby extends BorderPane {

	@FXML private TextField search;
	@FXML private Button submit;
	@FXML private CheckBox checkBoxFriends;
	@FXML private TilePane lobbyTiles;
	@FXML private BorderPane lobby = this;
	@FXML private HBox searchBar;
	@FXML private Button createGameBtn;

	public Lobby() {
		Resources.loadFXML(this);
		setSize();
		createGameBtn.setVisible(false);
		PokerApplication.networkStart = true;
		NetworkTester.testNetwork(this);
	}

	public void search() {
		lobbyTiles.getChildren().add(new Label(search.getText()));
	}
	@Handler
	public void startNetworkGame(NetworkClientEvent networkClientEvent){
		SceneController.setScene(new InGame());
	}

	public void friendBox() {
		lobbyTiles.getChildren()
				.add(new Label("Displaying table with friends"));
		//TODO display tables with friends in
	}

	public void closeLobby() {
		SceneController.setScene(new MainMenu());
	}

	public void keyPressed(KeyEvent event){
		if (event.getCode() == KeyCode.ESCAPE) closeLobby();
	}
	@FXML
	private void createGame(){
		System.out.println("starting network game");
		NetworkClient.setJoinMessage("join;1");
		SceneController.setScene(new InGame());
	}

	private void setSize() {
		searchBar.spacingProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.03));
		lobby.maxWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.7));
		lobby.maxHeightProperty().bind(
				PokerApplication.getRoot().heightProperty().multiply(0.7));
		search.prefWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.3));
		submit.prefWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.1));
	}

	public void keyListener(KeyEvent event){
		if (event.getCode() == KeyCode.ENTER) search();
	}


//	@Handler
	public void updateTables(String search) {
		if (checkBoxFriends.selectedProperty().get()) {
			// check for tables with friends on
		}
		// TODO query for all tables and add them as child to lobbyTiles
		if(search.contains("table")){
			createGameBtn.setVisible(true);
		}
	}
	
}
