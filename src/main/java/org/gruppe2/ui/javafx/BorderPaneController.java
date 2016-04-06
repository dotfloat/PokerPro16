package org.gruppe2.ui.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

/**
 * Created by kjors on 04.04.2016.
 */
public class BorderPaneController extends BorderPane {

    BorderPaneController() {

//        URL url = getClass().getResource("/views/" + getClass().getSimpleName() + ".fxml");
//        SceneController.setScene( url );



        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                    "/views/" + getClass().getSimpleName() + ".fxml"));

            if (fxmlLoader.getController() == null) {
                fxmlLoader.setController(this);
            }

            if (fxmlLoader.getRoot() == null) {
                fxmlLoader.setRoot(this);
            }

            this.setStyle("-fx-background-color: black");

            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
