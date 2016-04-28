package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
	@FXML private ImageView createGame;
	@FXML private ScrollPane scrollPane;

	public Lobby() {
		Resources.loadFXML(this);
		setSize();
		PokerApplication.networkStart = true;
//		NetworkTester.testNetwork(this);
	}

	public void search() {
		lobbyTiles.getChildren().add(new LobbyTable());
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
	@FXML
	private void createGame(){
		System.out.println("LOBBY: starting new network game");
		
		NetworkClient.setCreateMessage("create");
		SceneController.setScene(new InGame());
	}
	@FXML
	private void joinGame(){
		System.out.println("LOBBY: joining network game");
		NetworkClient.setJoinMessage("join;1");
		SceneController.setScene(new InGame());
	}

	private void setSize() {
		searchBar.spacingProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.03));
		lobby.maxWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.7));
		lobby.maxHeightProperty().bind(
				PokerApplication.getRoot().heightProperty().multiply(0.7));
		scrollPane.maxWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.7));
		scrollPane.maxHeightProperty().bind(
				PokerApplication.getRoot().heightProperty().multiply(0.7));
		search.prefWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.3));
		submit.prefWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.1));
		lobbyTiles.hgapProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.02));
		lobbyTiles.vgapProperty().bind(PokerApplication.getRoot().heightProperty().multiply(0.02));
		createGame.fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.13));
		createGame.preserveRatioProperty().setValue(true);
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
			//createGameBtn.setVisible(true);
		}

	}
	
}
