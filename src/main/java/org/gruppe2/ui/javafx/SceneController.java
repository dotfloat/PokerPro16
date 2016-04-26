package org.gruppe2.ui.javafx;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Created by kjors on 06.04.2016.
 * <p>
 * Changes scene in PokerApplication main root BorderPane #url = URL to
 * fxml-file
 */
class SceneController {
    public static StackPane stage = PokerApplication.getRoot();

    public static void setScene(Node node) {
        PokerApplication.getRoot().getChildren().set(0, node);
    }

    public static void setModal(Node node) {
        stage.getChildren().add(node);
    }

    public static void setModal(Node node, double x, double y) {
        if (x > PokerApplication.getRoot().getWidth() * 0.8) {
            x *= 0.8;
        }
        stage.getChildren().add(node);
    }
}
