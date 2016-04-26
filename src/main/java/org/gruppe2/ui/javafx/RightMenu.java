package org.gruppe2.ui.javafx;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import org.gruppe2.ui.Resources;

/**
 * Created by Petter on 11/04/2016.
 */
public class RightMenu extends VBox {

	@FXML private VBox innerBox;
	@FXML private ToggleButton showMenu;
	@FXML private Button viewLobby;
	@FXML private Button leaveTable;
	@FXML private Button viewStatistics;

	public RightMenu() {
		Resources.loadFXML(this);
		setSizes();
	}

	private void setSizes() {
		innerBox.prefWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.15));
		innerBox.maxWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.15));
		viewLobby.setMaxWidth(Double.MAX_VALUE);
		leaveTable.setMaxWidth(Double.MAX_VALUE);
		viewStatistics.setMaxWidth(Double.MAX_VALUE);
		innerBox.setVisible(false);

		viewLobby.fontProperty().bind(ChoiceBar.fontTracking);
		leaveTable.fontProperty().bind(ChoiceBar.fontTracking);
		viewStatistics.fontProperty().bind(ChoiceBar.fontTracking);

		this.maxWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.2));
		this.maxHeightProperty().bind(
				PokerApplication.getRoot().heightProperty().multiply(0.2));
	}


	public void leaveTable(ActionEvent actionEvent) {
		SceneController.setScene(new MainMenu());
	}

	public void viewLobby(ActionEvent actionEvent) {
		SceneController.setModal(new Lobby());
		menuAnimation();
	}

	public void viewStatistics(){
		SceneController.setModal(new Statistic(true));
	}

	public void showMenu(ActionEvent actionEvent) {
		menuAnimation();
	}

	private void menuAnimation() {
		if (!innerBox.isVisible()) {
			innerBox.setVisible(true);
			innerBox.setTranslateX(innerBox.getWidth());
		}
		TranslateTransition openMenu = new TranslateTransition(
				new Duration(300), innerBox);
		openMenu.setToX(0);
		TranslateTransition closeMenu = new TranslateTransition(new Duration(
				300), innerBox);
		if (innerBox.getTranslateX() != 0) {
			openMenu.play();
		} else {
			closeMenu.setToX(innerBox.getWidth());
			closeMenu.play();
		}
	}

}
