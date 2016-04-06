/**
 * Methods for the top menu
 * Must create "scene changer"
 *
 */

package org.gruppe2.ui.javafx;

import java.net.URL;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MenuBarController {

    @FXML
    private MenuItem settings;

    @FXML
    private MenuItem startGame;

    @FXML
    private MenuItem lobby;

    @FXML
    private MenuItem quit;

    @FXML
    void setScene(ActionEvent event) {

        // Get the id of the menu button clicked
        MenuItem item = (MenuItem) event.getSource();
        String optionClicked = item.getId();
        URL newScene;

        // Load the fxml-file corresponding to the button
        switch (optionClicked) {
            case "settings": newScene = getClass().getResource("/views/Settings.fxml");
                break;
            case "startGame": newScene = getClass().getResource("/views/GameWindow.fxml");
                break;
            case "lobby": newScene = getClass().getResource("/views/Lobby.fxml");
                break;
            default: newScene = getClass().getResource("/views/GameWindow.fxml");
                break;
        }

        // Call the scene-changer
        SceneController.setScene( newScene );
    }

    public void quit() { Platform.exit(); }
}
