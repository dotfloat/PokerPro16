package org.gruppe2.ui.javafx.menu;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import org.gruppe2.ai.NewDumbAI;
import org.gruppe2.network.MasterClient;
import org.gruppe2.network.TableEntry;
import org.gruppe2.ui.Resources;
import org.gruppe2.ui.javafx.PokerApplication;
import org.gruppe2.ui.javafx.SceneController;
import org.gruppe2.ui.javafx.ingame.InGame;

/**
 * Created by Petter on 11/04/2016.
 */

public class Lobby extends BorderPane {
	MasterClient masterClient;
	@FXML
	private TextField search;
	@FXML
	private Button submit;
	@FXML
	private CheckBox checkBoxFriends;
	@FXML
	private TilePane lobbyTiles;
	@FXML
	private BorderPane lobby = this;
	@FXML
	private HBox searchBar;
	@FXML
	private ImageView createGame;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private Pane tile;

	public Lobby() {
		PokerApplication.networkStart = true;
		masterClient = new MasterClient(this);
		Resources.loadFXML(this);
		setSize();
		
	}

	public void search() {
		lobbyTiles.getChildren().remove(1, lobbyTiles.getChildren().size());
		for (int i = 0; i < Math.random() * 10; i++) {
			Random random = new Random();
			int max = random.nextInt(7) + 3;
			int current = random.nextInt(max - 1) + 1;
			String players = current + "/" + max;
			String name = NewDumbAI.randomName() + "'s table";
			lobbyTiles.getChildren().add(new LobbyTable(players, name, this));
		}
	}

	

	public void friendBox() {
		lobbyTiles.getChildren()
				.add(new Label("Displaying table with friends"));
		// TODO display tables with friends in
	}

	@FXML
	private void requestCreateGame() {
		masterClient.requestCreateGame();
		
	}
	public void createGame(){
		SceneController.setScene(new InGame(masterClient.createNewTable()));
	}

	
	public void requestJoinGame(UUID uuid) {
		masterClient.requestJoinTable(uuid);
	}
	public void joinGame(){
		SceneController.setScene(new InGame(masterClient.joinTable()));
	}

	private void setSize() {
		searchBar.spacingProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.03));
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
		lobbyTiles.hgapProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.02));
		lobbyTiles.vgapProperty().bind(
				PokerApplication.getRoot().heightProperty().multiply(0.02));
		createGame.fitWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.13));
		createGame.preserveRatioProperty().setValue(true);
	}

	public void keyListener(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER)
			search();
	}

	
	public void updateTables(ArrayList<TableEntry> tablesInLobby) {
		if(tablesInLobby.size() == 0 )return;
		
		if (checkBoxFriends.selectedProperty().get()) {
			// check for tables with friends on
		}
		System.out.println("now updating tables");
		for(TableEntry table : tablesInLobby){
			String players = table.getCurrentPlayers()+"/"+table.getMaxPlayers();
			String name = table.getUUID().toString();
			lobbyTiles.getChildren().add(new LobbyTable(players, name, this));
		}
	}
}
