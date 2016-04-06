package org.gruppe2.ui.javafx;

/*
 * Created by Petter on 04/04/2016.
 */

import com.sun.scenario.animation.AnimationPulse;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Intro implements Initializable {

    @FXML private ImageView logo;
    @FXML private Label label;
    @FXML private BorderPane borderPane;

    public void goToMenu() throws IOException{
        SceneController.setScene((getClass().getResource("/views/MainMenu.fxml")));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert logo != null : "is null";
        logo.fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.8));

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1500), logo);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.1);
        fadeTransition.setCycleCount(Timeline.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();

    }
}
