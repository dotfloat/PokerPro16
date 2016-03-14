package org.gruppe2.frontend;

import org.gruppe2.backend.Player;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChoicePopup{
	
	
	public static void showChoices(GUI gui, Player player) {
		Platform.runLater(new Runnable(){
		    @Override
		    public void run() {

				HBox hbox = new HBox(200);
				hbox.setStyle("-fx-background-color: black");
				createGrid(hbox, gui.border, player);
		    	
		    }           
		});

	}
	private static void createGrid(HBox hbox,
			BorderPane border,  Player player) {

		Button check = new Button("Check");
		Button call = new Button("Call");
		Button fold = new Button("Fold");
		Button raise = new Button("Raise");
		Slider raiseSlider = new Slider(1,10000, 10);
		Label showCards = new Label("Cards will be shown here");
		
		setButtonAction(raiseSlider, check, call, raise,
				fold,  player);
		
		
		
		hbox.getChildren().addAll(fold,call,check,raiseSlider,raise,showCards);
		border.setBottom(hbox);
	}

	private static void setButtonAction(Slider raiseSlider,
			Button check, Button call,
			Button raise, Button fold,
			 Player player) {
		check.setMaxHeight(100);
		check.setMaxWidth(500);
//		check.setOnAction(e -> {
//			player.doAction(Action.CHECK);
//		});
//		
//		call.setOnAction(e -> {
//			player.doAction(Action.CALL);
//		});
//		
//		fold.setOnAction(e -> {
//			player.doAction(Action.FOLD);
//		});
		
		//Slider
		raiseSlider.setShowTickMarks(true);
		raiseSlider.setShowTickLabels(true);
		raiseSlider.setMajorTickUnit(5f);
		raiseSlider.setBlockIncrement(5f);
		
		
//		raiseSlider.setOnAction(e -> {
//			if (!check.getText().equals(null)) {
//					if(!raiseField.getText().equals(null)){
//						player.doAction(Action.RAISE);
//						dialogStage.close();
//					}
//					
//			}
//			
//			else
//				System.out.println("No name");
//		});
	

	}

}
