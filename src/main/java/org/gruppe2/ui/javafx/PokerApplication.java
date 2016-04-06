/**
 * Setting up the stage, and default start scene
 * May implement alt start...
 */

package org.gruppe2.ui.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.gruppe2.game.old.Player;
import org.gruppe2.ui.old_javafx.GUIClient;

import java.net.URL;

public class PokerApplication extends Application {

    // Setting global root. Will only change scenes
    private static BorderPane root = new BorderPane();

    // Controllers will need to get current root to change scenes
    public static BorderPane getRoot() {
        return root;
    }

    @Override
    public void start(Stage stage) throws Exception {
//        BorderPane root = new BorderPane();
//        root.getChildren().add(new GameWindow());

        // Menu-bar always present
        URL menuUrl = getClass().getResource("/views/MenuBar.fxml");
        MenuBar menu = FXMLLoader.load( menuUrl );
        root.setTop( menu );


        // Set default scene
//        URL gameWindowUrl = getClass().getResource("/views/GameWindow.fxml");
        BorderPane gameWindow = new GameWindow();

        //Set stage
        Scene scene = new Scene(root, 1280, 786);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setTitle("PokerPro16");
        stage.setScene(scene);
        stage.show();
    }
}
