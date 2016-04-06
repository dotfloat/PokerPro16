package org.gruppe2.ui.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

class VBoxController extends VBox {
	
    VBoxController() {
        /* Java doesn't have templates so we have to resort to writing boilerplate code -.-  */
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                    "/views/" + getClass().getSimpleName() + ".fxml"));

            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);

            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
