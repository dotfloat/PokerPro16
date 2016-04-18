package org.gruppe2.ui.javafx;

/*
 * Created by Petter on 04/04/2016.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import org.gruppe2.ui.Resources;

public class Intro extends StackPane {

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
