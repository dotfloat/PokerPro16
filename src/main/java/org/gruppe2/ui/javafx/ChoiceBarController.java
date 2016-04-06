package org.gruppe2.ui.javafx;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

public class ChoiceBarController {
	ChoiceBarController() {
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
