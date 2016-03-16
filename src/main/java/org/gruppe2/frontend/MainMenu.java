package org.gruppe2.frontend;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainMenu extends Pane {
	ImageView imageView;
	
	
	public void setMainMenu(Stage primaryStage, Group root, GUI gui) {

		setMainBackGround(root, gui);
		setButtons(root, gui, primaryStage );

		this.setMinHeight(gui.getHeight());
		this.setMaxHeight(gui.getHeight());
		gui.updateStageDimensions();

		getScene().setFill(Color.BLACK);
		
	}

	private void setButtons(Group root, GUI gui, Stage primaryStage) {
		gui.canvas.setHeight(gui.getHeight());
		VBox vbox = new VBox(10);
		double buttonWidth = gui.getWidth()*0.4;
		double buttonHeight = gui.getHeight()*0.05;
		Button createTable = new Button("CREATE TABLE");
		Button joinTable = new Button("JOIN TABLE");
		Button singlePlayer = new Button("SINGLE PLAYER");
		Button settings = new Button("SETTINGS");

		createTable.getStyleClass().add("buttonMenu");
		joinTable.getStyleClass().add("buttonMenu");
		singlePlayer.getStyleClass().add("buttonMenu");
		settings.getStyleClass().add("buttonMenu");

		//want to this with css, but haven't found out how just yet
		createTable.setPrefWidth(buttonWidth);
		joinTable.setPrefWidth(buttonWidth);
		singlePlayer.setPrefWidth(buttonWidth);
		settings.setPrefWidth(buttonWidth);

		//want to this with css, but haven't found out how just yet
		createTable.setPrefHeight(buttonHeight);
		joinTable.setPrefHeight(buttonHeight);
		singlePlayer.setPrefHeight(buttonHeight);
		settings.setPrefHeight(buttonHeight);
		
		setActionOnButtons(createTable, joinTable, singlePlayer, settings, gui , root, vbox, primaryStage);
		vbox.getChildren().addAll(createTable,joinTable,singlePlayer,settings);
		
		vbox.setLayoutX(gui.getWidth()*0.5 - buttonWidth*0.5);
		vbox.setLayoutY(gui.getHeight()*0.5 + buttonHeight*3);

		
		this.getChildren().add(vbox);
		root.getChildren().add(this);
	
	}

	private void setActionOnButtons(Button createTable, Button joinTable,
			Button singlePlayer, Button settings, GUI gui, Group root, VBox vbox, Stage primaryStage) {
		createTable.setOnAction(e -> 
		{
			System.out.println("Not yet implemented, wait for next iteration");
		});
		joinTable.setOnAction(e -> 
		{
			System.out.println("Not yet implemented, wait for next iteration");
		});
		singlePlayer.setOnAction(e -> 
		{
			this.getChildren().remove(vbox);
			InitializeGame.setStartValues(gui, root, primaryStage);
		});
		settings.setOnAction(e -> 
		{
			this.getChildren().remove(vbox);
			SettingsMenu settingsMenu = new SettingsMenu(this,gui,root);
			settingsMenu.showSettings();
		});
		
	}

	private void setMainBackGround(Group root, GUI gui) {
		
		imageView = new ImageView (new Image(getClass().getResourceAsStream("/pokerWhite.png")));
		
		imageView.setFitWidth(gui.getWidth()*0.9);
		imageView.setPreserveRatio(true);
		imageView.setLayoutX(gui.getWidth()/2-imageView.getFitWidth()/2);
		imageView.setLayoutY(gui.getWidth()*0.05);
		
		this.getChildren().add(imageView);
	}
	
	
	

}
