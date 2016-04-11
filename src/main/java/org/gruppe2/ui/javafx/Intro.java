package org.gruppe2.ui.javafx;

/*
 * Created by Petter on 04/04/2016.
 */

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.gruppe2.ui.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Intro extends BorderPane {

    @FXML private ImageView logo;
    @FXML private Label clickToContinue;

    public Intro(){
        Resources.loadFXML(this);
        logo.fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.8));
        clickToContinueFading(clickToContinue);
    }
    public void goToMenu() throws IOException{
        System.out.println("Clicked.");
        Resources.loadFXML(new RightMenu());
        //SceneController.setScene((getClass().getResource("/views/MainMenu.fxml")));
    }

    private void fallingAnimation(Rectangle node){
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(node.yProperty(), 500);
        final KeyFrame kf = new KeyFrame(Duration.millis(4000), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    private void clickToContinueFading(Node node){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(700), node);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.1);
        fadeTransition.setCycleCount(Timeline.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();

    }
}
