package org.gruppe2.ui.javafx;

/*
 * Created by Petter on 04/04/2016.
 */

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.gruppe2.ui.Resources;

import java.io.IOException;

public class Intro extends StackPane {

	@FXML
	private ImageView logo;
	@FXML
	private Label clickToContinue;
	private FadeTransition fadeTransition;

	public Intro() {
		Resources.loadFXML(this);
//		SoundPlayer.playIntroMusic();   IF YOU WANT MUSIC PUT THIS ON
		logo.fitWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.8));
		clickToContinueFading(clickToContinue);
	}

	public void goToMenu() throws IOException {
		fadeTransition.stop();
		ConfettiOrMoney.stopAllAnimations();
		SceneController.setScene(new MainMenu());
	}

	@FXML
	public void proceed(KeyEvent event){
		if (event.getCode() == KeyCode.ENTER) System.out.println("hola");
		SceneController.setScene(new MainMenu());
	}


	private void clickToContinueFading(Node node) {
		fadeTransition = new FadeTransition(Duration.millis(700), node);
		fadeTransition.setFromValue(1.0);
		fadeTransition.setToValue(0.1);
		fadeTransition.setCycleCount(100);
		fadeTransition.setAutoReverse(true);
		fadeTransition.play();
	}
}
