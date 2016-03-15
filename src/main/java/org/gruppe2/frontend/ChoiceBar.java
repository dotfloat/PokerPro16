package org.gruppe2.frontend;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

import org.gruppe2.backend.Action;
import org.gruppe2.backend.Player;
/**
 * This is the bottom buttons, textfields and slider.
 * @author htj063
 *
 */
public class ChoiceBar{
	
	
	public static void showChoices(GUI gui, Player player) {
		Platform.runLater(new Runnable(){
		    @Override
		    public void run() {
		    	
				HBox hbox = new HBox(gui.getWidth()*0.025);
				hbox.getStyleClass().add("hbox");
				hbox.setAlignment(Pos.CENTER);
				createGrid( gui, hbox,  player);
		    }           
		});
	}
	private static void createGrid(GUI gui, HBox hbox, Player player) {

		Button check = new Button("CHECK");
		Button call = new Button("CALL");
		Button fold = new Button("FOLD");
		Button raise = new Button("RAISE");
		Slider raiseSlider = new Slider(25, 5000, 50);
		raiseSlider.setMaxWidth(gui.getWidth()*0.23);
		raiseSlider.setMinWidth(gui.getWidth()*0.23);
		Label sliderValue = new Label((int)raiseSlider.getValue() + " CHIPS");
		sliderValue.setMinWidth(gui.getWidth()*0.055);
		sliderValue.setMaxWidth(gui.getWidth()*0.055);
		Label showCards = new Label("Cards will be shown here");
		showCards.setMinWidth(gui.getWidth()*0.2);
		showCards.setMaxWidth(gui.getWidth()*0.2);

		setButtonAction(raiseSlider, check, call, raise, fold, player, sliderValue, gui.getClient());
		
		
		
		hbox.getChildren().addAll(fold,call,check,raiseSlider,sliderValue, raise, showCards);
		setHBoxSize(hbox, gui);
		
		gui.getBorder().setBottom(hbox);
	}
	


	private static void setButtonAction(Slider raiseSlider,
										Button check, Button call,
										Button raise, Button fold,
										Player player, Label sliderValue, GUIClient client) {
		
		check.setOnAction(e -> client.setAction(new Action.Check()));
//		
//		call.setOnAction(e -> {
//			player.doAction(Action.CALL);
//		});
//		
//		fold.setOnAction(e -> {
//			player.doAction(Action.FOLD);
//		});
		
		//Slider
		raiseSlider.setMajorTickUnit(10);
		raiseSlider.setBlockIncrement(10);
		raiseSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				sliderValue.textProperty().setValue(checkMaxBid(raiseSlider));
				//uncomment when there is an actual player
				//raiseSlider.setMin(player.getBank());
				//raiseSlider.setMax(player.getBank());
			}
		});
		

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
	private static String checkMaxBid(Slider slider){
		if (slider.getValue()==slider.getMax()) return "GO ALL IN";
		else return (int) slider.getValue() + " CHIPS";
	}
	
	private static void setHBoxSize(HBox hbox, GUI gui) {
		hbox.setMinHeight(gui.getHeight()*0.055);
		hbox.setMaxHeight(gui.getHeight()*0.055);
//		hbox.setMinHeight(70);
//		hbox.setMaxHeight(70);
		
	}

}
