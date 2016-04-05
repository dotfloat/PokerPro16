/**
 * Methods for the top menu
 * Must create "scene changer"
 *
 */

package org.gruppe2.ui.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.net.URL;

public class MenuBarController {

    @FXML
    private MenuItem settings;

    @FXML
    private MenuItem startGame;

    @FXML
    void setSceneSettings(ActionEvent event) {
        try {
            URL settingsUrl = getClass().getResource("/view/Settings.fxml");
            GridPane settings = FXMLLoader.load( settingsUrl );

            BorderPane border = PokerApplication.getRoot();
            border.setCenter( settings );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void setSceneGame(ActionEvent event) {
        try {
            URL gameUrl = getClass().getResource("/view/GameWindow.fxml");
            BorderPane game = FXMLLoader.load( gameUrl );

            BorderPane border = PokerApplication.getRoot();
            border.setCenter( game );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
