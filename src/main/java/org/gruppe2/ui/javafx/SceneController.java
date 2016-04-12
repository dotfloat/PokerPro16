package org.gruppe2.ui.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

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

    public static void setModal(Node node){
        StackPane stage = PokerApplication.getRoot();
        if (stage.getChildren().size() == 2) {
            stage.getChildren().add(node);
        } else {
            stage.getChildren().set(2, node);
        }
    }
    public static void removeModal(Node node){
        StackPane stage = PokerApplication.getRoot();
        stage.getChildren().removeAll(node);
    }
    public static void setMenuButton(Node node){
        StackPane stage = PokerApplication.getRoot();
        stage.getChildren().add(node);
    }

}
