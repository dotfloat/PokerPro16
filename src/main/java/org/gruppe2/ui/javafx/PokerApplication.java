/**
 * Setting up the stage, and default start scene
 * May implement alt start...
 */

package org.gruppe2.ui.javafx;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PokerApplication extends Application {
	private static int width;
	private static int height;

    // Setting global root. Will only change scenes
    private static BorderPane root = new BorderPane();

    // Controllers will need to get current root to change scenes
    public static BorderPane getRoot() {
        return root;
    }

    @Override
    public void start(Stage stage) throws Exception {

        // Lets load startValues from settings-file or something...
    	startValues();
        stage.setTitle("PokerPro16");


        /**
         * Menu-bar always present in main stage
         * Let's pretend this is just temporary
         */
        URL menuUrl = getClass().getResource("/views/MenuBar.fxml");
        MenuBar menu = FXMLLoader.load( menuUrl );
        root.setTop( menu );


        /**
         * Set start-scene to intro
         *
         * when changing scenes later, use SceneController.setScene()
         * Mvh Kjetil
         */
        URL introSceneUrl = getClass().getResource("/views/Intro.fxml");
        SceneController.setScene( introSceneUrl );


        /**
         * Set up stage
         *
         * No global stylesheet in javaFX 8 stage, only on every scene
         */
        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

	private void startValues() {
		width = 1280;
		height = 768;	
	}
	public static int getWidth(){
		return width;
	}
	public static int getHeight(){
		return height;
	}
}
