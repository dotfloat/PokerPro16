package org.gruppe2.ui.javafx.menu;

import java.util.ArrayList;
import java.util.UUID;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import org.gruppe2.network.MasterClient;
import org.gruppe2.network.TableEntry;
import org.gruppe2.ui.UIResources;
import org.gruppe2.ui.javafx.Modal;
import org.gruppe2.ui.javafx.PokerApplication;
import org.gruppe2.ui.javafx.SceneController;
import org.gruppe2.ui.javafx.ingame.Game;
import org.gruppe2.ui.javafx.ingame.GameScene;

/**
 * Created by Petter on 11/04/2016.
 */

public class Lobby extends BorderPane {
	MasterClient masterClient;
	@FXML
	private TextField search;
	@FXML
	private Button refresh;
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
		masterClient = new MasterClient(this);
		UIResources.loadFXML(this);
		setSize();
		
	}

	public void refresh() {
		masterClient.search();
	}

	

	public void friendBox() {
//		lobbyTiles.getChildren()
//				.add(new Label("Displaying table with friends"));
		// TODO display tables with friends in
	}

	@FXML
	private void requestCreateGame() {
        CreateGameSettings.show(masterClient);
	}

	public void createGame(){
		Game.getInstance().setContext(masterClient.createNewTable());
		SceneController.setOnlyThisScene(new GameScene());
	}

	
	public void requestJoinGame(UUID uuid) {
		masterClient.requestJoinTable(uuid);
	}

	public void joinGame(){
		Game.getInstance().setContext(masterClient.joinTable());
		SceneController.setOnlyThisScene(new GameScene());
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
		refresh.prefWidthProperty().bind(
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
			refresh();
	}

	
	public void updateTables(ArrayList<TableEntry> tablesInLobby) {
		if(tablesInLobby.size() == 0 )return;
		System.out.println("size of tables: "+tablesInLobby.size());
		for(int i = 1;i<lobbyTiles.getChildren().size();i++)
			lobbyTiles.getChildren().remove(i);
		
		if (checkBoxFriends.selectedProperty().get()) {
			// check for tables with friends on
		}
		for(TableEntry table : tablesInLobby){
			
			String players = table.getCurrentPlayers()+"/"+table.getMaxPlayers();
			String name = table.getName().isEmpty() ? table.getUUID().toString() : table.getName();
			
			lobbyTiles.getChildren().add(new LobbyTable(players, table.getUUID(), name, this));
		}
	}

    public static void show() {
        Modal modal = new Modal(true);
        modal.setPercentSize(0.8, 0.8);
        modal.setContent(new Lobby());
        modal.show();
    }
}
