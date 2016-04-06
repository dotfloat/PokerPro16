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
	private int width;
	private int height;
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

//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
//                    "/views/" + getClass().getSimpleName() + ".fxml"));
//
//        fxmlLoader.setController( this );
//        fxmlLoader.setRoot( root );
//
//        System.out.println( "fxmlLoader file: " + fxmlLoader.getLocation().toString());
//        System.out.println( "fxmlLoader root: " + fxmlLoader.getRoot().toString());

        /**
         * Menu-bar always present in main stage
         * Let's pretend this is just temporary
         */

        URL menuUrl = getClass().getResource("/views/MenuBar.fxml");
        MenuBar menu = FXMLLoader.load( menuUrl );
        root.setTop( menu );


        /**
         * Set start-scene (intro)
         *
         * when changing scenes later, use SceneController.setScene()
         * Mvh Kjetil
         */
//        URL introSceneUrl = getClass().getResource("/views/Intro.fxml");
//        SceneController.setScene( introSceneUrl );

        BorderPane introScene = new GameWindow();



        /**
         * Set up stage
         *
         * NEVER use setRoot later, only SceneController
         */
        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setTitle("PokerPro16");
        stage.setScene(scene);
        stage.show();
    }

	private void startValues() {
		width = 1280;
		height = 768;	
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
}
