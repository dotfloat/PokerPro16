package org.gruppe2.ui.javafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

class CloseMe extends VBox {
    CloseMe() {
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

    @FXML
    public void quit() {
        Platform.exit();
    }
}
