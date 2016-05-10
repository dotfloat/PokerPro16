package org.gruppe2.ui.javafx.menu;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import org.gruppe2.game.session.ClientSession;
import org.gruppe2.game.session.Session;
import org.gruppe2.network.MasterClient;
import org.gruppe2.ui.UIResources;
import org.gruppe2.ui.javafx.Modal;
import org.gruppe2.ui.javafx.PokerApplication;
import org.gruppe2.ui.javafx.SceneController;
import org.gruppe2.ui.javafx.ingame.Game;
import org.gruppe2.ui.javafx.ingame.GameScene;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 */
public class MainMenu extends BorderPane {
	@FXML
	private ImageView logo;
	@FXML
	private Button online;
	@FXML
	private Button singlePlayer;
	@FXML
	private Button viewStatistics;
	@FXML
	private Button settings;
	@FXML
	private Button testClient;
	@FXML
	private VBox vBox;

	public MainMenu() {
		UIResources.loadFXML(this);
		logo.fitWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.8));
		vBox.maxWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.3));

		setButtonSize();
		fadeIn();

	}

	public void goToSinglePlayer() throws InterruptedException {
		Game.autostart();
		Game.getInstance().join();
		SceneController.setScene(new GameScene());
	}

	public void goToTestServer() {
		Game.autostart();
		Game.getContext().waitReady();
		Game.message("listen");
		SceneController.setScene(new GameScene());
	}

	public void goToTestClient() {
		Game.getInstance().setContext(Session.start(ClientSession.class, "localhost"));
		Game.getContext().waitReady();
		SceneController.setScene(new GameScene());
	}

	public void createNetWorkTable() {
		SceneController.setScene(new GameScene());
	}

	public void goToLobby() {
		
		if(MasterClient.localMasterServerIsUp())
			SceneController.setModal(new Modal(new Lobby()));
		else if(MasterClient.onlineMasterServerIsUp())
			SceneController.setModal(new Modal(new Lobby()));
		else
			System.out.println("no master server is up");
	}

	public void goToStatistics() {
		throw new NotImplementedException();
	}

	public void goToSettings() {
		throw new NotImplementedException();
	}

	private void fadeIn() {
		FadeTransition fadeTransition = new FadeTransition(
				Duration.millis(800), vBox);
		fadeTransition.setFromValue(0.0);
		fadeTransition.setToValue(1.0);
		fadeTransition.play();
	}

	private void setButtonSize() {
		online.setMaxWidth(Double.MAX_VALUE);
		singlePlayer.setMaxWidth(Double.MAX_VALUE);
		viewStatistics.setMaxWidth(Double.MAX_VALUE);
		settings.setMaxWidth(Double.MAX_VALUE);
		testClient.setMaxWidth(Double.MAX_VALUE);
	}
}
