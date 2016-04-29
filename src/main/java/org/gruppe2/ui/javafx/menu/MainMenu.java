package org.gruppe2.ui.javafx.menu;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import org.gruppe2.ui.Resources;
import org.gruppe2.ui.javafx.PokerApplication;
import org.gruppe2.ui.javafx.SceneController;
import org.gruppe2.ui.javafx.ingame.InGame;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 */
public class MainMenu extends BorderPane {
	@FXML
	private ImageView logo;
	@FXML
	private Button createTable;
	@FXML
	private Button joinTable;
	@FXML
	private Button singlePlayer;
	@FXML
	private Button viewStatistics;
	@FXML
	private Button settings;
	@FXML
	private VBox vBox;

	public MainMenu() {
		Resources.loadFXML(this);

		logo.fitWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.8));
		vBox.maxWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.3));

		setButtonSize();
		fadeIn();

	}

	public void goToSinglePlayer() throws InterruptedException {
		SceneController.setScene(new InGame());
	}

	public void goToTestClient() {
		PokerApplication.networkStart = true;
		SceneController.setScene(new InGame());
	}

	public void createNetWorkTable() {
		SceneController.setScene(new InGame());
	}

	public void goToLobby() {
		SceneController.setScene(new Lobby());
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
		createTable.setMaxWidth(Double.MAX_VALUE);
		joinTable.setMaxWidth(Double.MAX_VALUE);
		singlePlayer.setMaxWidth(Double.MAX_VALUE);
		viewStatistics.setMaxWidth(Double.MAX_VALUE);
		settings.setMaxWidth(Double.MAX_VALUE);

	}
}
