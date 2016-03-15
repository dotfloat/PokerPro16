package org.gruppe2.frontend;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Pane {
	ImageView imageView;
	
	
	public void setMainMenu(Stage primaryStage, Group root, GUI gui) {
		setMainBackGround(root, gui);
		this.setStyle("-fx-background-color: black");
		setButtons(root, gui, primaryStage );
		
		
		
		
	}

	private void setButtons(Group root, GUI gui, Stage primaryStage) {
		gui.canvas.setHeight(gui.getHeight());
		VBox vbox = new VBox();
		Button createTable = new Button("CREATE TABLE");
		Button joinTable = new Button("JOIN TABLE");
		Button singlePlayer = new Button("SINGLE PLAYER");
		Button settings = new Button("SETTINGS");
		
		setActionOnButtons(createTable, joinTable, singlePlayer, settings, gui , root, vbox, primaryStage);
		vbox.getChildren().addAll(createTable,joinTable,singlePlayer,settings);
		
		vbox.setLayoutX(gui.getWidth()/2 - vbox.getWidth());
		vbox.setLayoutY(gui.getHeight()/2 + gui.getHeight()*0.2);
		vbox.setAlignment(Pos.CENTER);
		
		
		
		this.getChildren().add(vbox);
		root.getChildren().add(this);
//		root.setStyle("-fx-background-color: black");
		
		
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
			root.getChildren().remove(vbox);
			InitializeGame.setStartValues(gui, root, primaryStage);
		});
		settings.setOnAction(e -> 
		{
			System.out.println("Not yet implemented, wait for next iteration");
		});
		
	}

	private void setMainBackGround(Group root, GUI gui) {
		
		imageView = new ImageView (new Image(getClass().getResourceAsStream("/pokerWhite.png")));
		
		imageView.setFitWidth(gui.getWidth());
		imageView.setFitHeight(gui.getHeight());
		
		imageView.setLayoutX(gui.getWidth()/2-imageView.getFitWidth()/2);
		imageView.setLayoutY(gui.getWidth()*0.05);
		imageView.setPreserveRatio(true);
		
		this.getChildren().add(imageView);
	}
	
	

}
