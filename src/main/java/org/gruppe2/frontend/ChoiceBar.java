package org.gruppe2.frontend;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import org.gruppe2.backend.Player;

public class ChoiceBar{
	
	
	public static void showChoices(GUI gui, Player player) {
		Platform.runLater(new Runnable(){
		    @Override
		    public void run() {
		    	
				HBox hbox = new HBox(200);
				hbox.getStyleClass().add("hbox");
				hbox.setAlignment(Pos.CENTER);
				createGrid(hbox, gui.border, player);
		    	
		    }           
		});
	}
	private static void createGrid(HBox hbox,
			BorderPane border,  Player player) {

		Button check = new Button("CHECK");
		Button call = new Button("CALL");
		Button fold = new Button("FOLD");
		Button raise = new Button("RAISE");
		Slider raiseSlider = new Slider(1,10000, 10);
		Label showCards = new Label("Cards will be shown here");
		
		setButtonAction(raiseSlider, check, call, raise,
				fold,  player);
		
		
		
		hbox.getChildren().addAll(fold,call,check,raiseSlider,raise,showCards);
		hbox.setMinHeight(70);
		hbox.setMaxHeight(70);
		
		border.setBottom(hbox);
		
		
	}

	private static void setButtonAction(Slider raiseSlider,
			Button check, Button call,
			Button raise, Button fold,
			 Player player) {
		
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
		raiseSlider.setShowTickMarks(false);
		raiseSlider.setShowTickLabels(false);
		raiseSlider.setMajorTickUnit(5);
		raiseSlider.setBlockIncrement(5);
		
		
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
