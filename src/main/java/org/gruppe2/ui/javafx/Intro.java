package org.gruppe2.ui.javafx;

/*
 * Created by Petter on 04/04/2016.
 */

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import org.gruppe2.ui.Resources;

public class Intro extends BorderPane {

	@FXML
	private ImageView logo;
	@FXML
	private Label clickToContinue;

	public Intro() {
		Resources.loadFXML(this);
		logo.fitWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.8));
		clickToContinueFading(clickToContinue);
	}

	public void goToMenu() throws IOException {
		SceneController.setScene(new MainMenu());
	}

	@FXML
	public void proceed(KeyEvent event){
		if (event.getCode() == KeyCode.ENTER) System.out.println("hola");
		SceneController.setScene(new MainMenu());
	}

	@SuppressWarnings("unused")
	private void fallingAnimation(Rectangle node) {
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		final KeyValue kv = new KeyValue(node.yProperty(), 500);
		final KeyFrame kf = new KeyFrame(Duration.millis(4000), kv);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

	private void clickToContinueFading(Node node) {
		FadeTransition fadeTransition = new FadeTransition(
				Duration.millis(700), node);
		fadeTransition.setFromValue(1.0);
		fadeTransition.setToValue(0.1);
		fadeTransition.setCycleCount(Timeline.INDEFINITE);
		fadeTransition.setAutoReverse(true);
		fadeTransition.play();

	}
}
