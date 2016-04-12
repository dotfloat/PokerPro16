package org.gruppe2.ui.javafx;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * Created by kjors on 06.04.2016.
 *
 * Changes scene in PokerApplication main root BorderPane #url = URL to
 * fxml-file
 */
class SceneController {

	public static void setScene(Node node) {
		PokerApplication.getRoot().getChildren().set(0, node);
	}

	public static void setModal(Node node) {
		StackPane stage = PokerApplication.getRoot();
		if (stage.getChildren().size() == 1) {
			stage.getChildren().add(node);
			stage.getChildren().get(0).disableProperty().setValue(true);
		} else {
			stage.getChildren().set(1, node);
		}
	}

	public static void removeModal(Node node) {
		StackPane stage = PokerApplication.getRoot();
		stage.getChildren().removeAll(node);
		stage.getChildren().get(0).disableProperty().setValue(false);
	}

	public static void setMenuButton(Node node) {
		StackPane stage = PokerApplication.getRoot();
		stage.getChildren().add(node);
	}

}
