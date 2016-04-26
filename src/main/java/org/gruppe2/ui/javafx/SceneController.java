package org.gruppe2.ui.javafx;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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

	public static void setStatistic(Node node, double x, double y){
		StackPane stage = PokerApplication.getRoot();
		Pane pane = new Pane(node);
		if (x > PokerApplication.getRoot().getWidth()* 0.8) {
			x *= 0.8;
		}

		pane.getChildren().get(0).setLayoutX(x);
		pane.getChildren().get(0).setLayoutY(y);

		if (stage.getChildren().size() == 1) {
			stage.getChildren().add(pane);
			stage.getChildren().get(0).disableProperty().setValue(true);
		} else {
			stage.getChildren().set(1, pane);
		}
	}

	public static void removeStatistic(Node node){
		StackPane stage = PokerApplication.getRoot();
		stage.getChildren().remove(stage.getChildren().size()-1);
		stage.getChildren().get(0).disableProperty().setValue(false);
	}
}