/**
 * Setting up the stage, and default start scene
 * May implement alt start...
 */

package org.gruppe2.ui.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.gruppe2.Main;

public class PokerApplication extends Application {
	private static int width;
	private static int height;

    private static int numberOfPlayers; // Should reside in model / game?
    private Scene scene;
    private static StackPane root = new StackPane(); // Setting global root. Will only change scenes

    /** Controllers will need to get current root to change scenes
     * @return root
     */
    public static StackPane getRoot() {
        return root;
    }

    @Override
    public void start(Stage stage) throws Exception {

    	startValues(stage);
        setStartScene(stage); 
    }

	private void startValues(Stage stage) {
		width = 1280;
		height = 768;	
		stage.setTitle("PokerPro16");
		stage.getIcons().add(new Image("/images/ui/icon.png"));
	}
	
	public static int getWidth(){
		return width;
	}
	
	public static int getHeight(){
		return height;
	}
	
	/**
     * Set up scene and stage
     * Starts the intro
     * No global stylesheet in javaFX 8 stage, only on every scene
     */
	private void setStartScene(Stage stage){
		if(Main.isAutostart())
			root.getChildren().add(new GameWindow());
		else
			root.getChildren().add(new Intro());
		
		scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
	}

    public static int getNumberOfPlayers() { return numberOfPlayers;}
}
