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

    	startValues();
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
