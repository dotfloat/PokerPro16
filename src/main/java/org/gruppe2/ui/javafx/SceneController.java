package org.gruppe2.ui.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

/**
 * Created by kjors on 06.04.2016.
 *
 * Changes scene in PokerApplication main root BorderPane
 * #url = URL to fxml-file
 */
class SceneController {

    public static void setScene(URL url) {

        try {
        	
            BorderPane newScene = FXMLLoader.load( url );
            StackPane stage = PokerApplication.getRoot();
            stage.getChildren().set(0, newScene);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
