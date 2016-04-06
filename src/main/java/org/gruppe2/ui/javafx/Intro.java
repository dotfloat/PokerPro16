package org.gruppe2.ui.javafx;

/*
 * Created by Petter on 04/04/2016.
 */

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.gruppe2.game.logic.*;

public class Intro implements Initializable {

    @FXML private ImageView logo;
    @FXML private Label clickToContinue;

    public void goToMenu() throws IOException{
        SceneController.setScene((getClass().getResource("/views/MainMenu.fxml")));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert logo != null : "is null";
        logo.fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.8));

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(700), clickToContinue);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.1);
        fadeTransition.setCycleCount(Timeline.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();

//        Soundplayer introSong = new Soundplayer("Crazy_Bone_Rag.mid");
//        introSong.play();

    }
}
