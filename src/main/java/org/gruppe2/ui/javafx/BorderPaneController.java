package org.gruppe2.ui.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Created by kjors on 04.04.2016.
 */
public class BorderPaneController extends BorderPane {

    BorderPaneController() {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                    "/views/" + getClass().getSimpleName() + ".fxml"));
            fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
