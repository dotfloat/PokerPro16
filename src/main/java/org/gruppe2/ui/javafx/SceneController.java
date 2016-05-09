package org.gruppe2.ui.javafx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * Created by kjors on 06.04.2016.
 * <p>
 * Changes scene in PokerApplication main root BorderPane #url = URL to
 * fxml-file
 */
public class SceneController {
    public static StackPane stage = PokerApplication.getRoot();

    public static void setScene(Node node) {
        PokerApplication.getRoot().getChildren().set(0, node);
    }

    public static void setModal(Node node) {
        stage.getChildren().add(node);
    }
    public static void removeNodal(Node node){
    	 stage.getChildren().remove(node);
    }
    
    public static void setFadingModal(Node node){
    	stage.getChildren().add(node);
    	Timeline timeline = new Timeline(new KeyFrame(
    	        Duration.seconds(4),
    	        ae -> removeNodal(node)));
    	timeline.play();
    }

    public static void setTooltip(Node node, double x, double y) {
        if (x > PokerApplication.getRoot().getWidth() * 0.8) x *= 0.8;
        Pane pane = (Pane) node;
        pane.getChildren().get(0).setLayoutY(y);
        pane.getChildren().get(0).setLayoutX(x);
        stage.getChildren().add(new ToolTip(pane));
    }
}
