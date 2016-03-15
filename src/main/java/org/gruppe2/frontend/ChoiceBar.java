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
import org.gruppe2.backend.Action.Fold;
import org.gruppe2.backend.Player;

/**
 * This is the bottom buttons, textfields and slider.
 * 
 * @author htj063
 *
 */
public class ChoiceBar {

	public static void showChoices(GUI gui, Player player) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				HBox hbox = new HBox(gui.getWidth() * 0.025);
				hbox.getStyleClass().add("hbox");
				hbox.setAlignment(Pos.CENTER);
				createGrid(gui, hbox, player);
			}
		});
	}

	private static void createGrid(GUI gui, HBox hbox, Player player) {

		Button check = new Button("CHECK");
		Button call = new Button("CALL");
		Button fold = new Button("FOLD");
		Button raise = new Button("RAISE");
		Slider raiseSlider = new Slider(25, 5000, 50);
		raiseSlider.setMaxWidth(gui.getWidth() * 0.23);
		raiseSlider.setMinWidth(gui.getWidth() * 0.23);
		Label sliderValue = new Label((int) raiseSlider.getValue() + " CHIPS");
		sliderValue.setMinWidth(gui.getWidth() * 0.1);
		sliderValue.setMaxWidth(gui.getWidth() * 0.1);
		Label showCards = new Label("");
		showCards.setMinWidth(gui.getWidth() * 0.2);
		showCards.setMaxWidth(gui.getWidth() * 0.2);

		setButtonAction(raiseSlider, check, call, raise, fold, player,
				sliderValue, gui.getClient());

		hbox.getChildren().addAll(fold, call, check, raiseSlider, sliderValue,
				raise, showCards);
		setHBoxSize(hbox, gui);
		
		setKeyListener(check, call, fold, raise, gui, gui.getClient(), raiseSlider,player);

		gui.getBorder().setBottom(hbox);
	}

	private static void setButtonAction(Slider raiseSlider, Button check,
			Button call, Button raise, Button fold, Player player,
			Label sliderValue, GUIClient client) {

		check.setOnAction(e -> client.setAction(new Action.Check()));

		call.setOnAction(e -> client.setAction(new Action.Call()));

		fold.setOnAction(e -> client.setAction(new Action.Fold()));

		// Slider
		raiseSlider.setMajorTickUnit(10);
		raiseSlider.setBlockIncrement(10);
		raiseSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				sliderValue.textProperty().setValue(checkMaxBid(raiseSlider));
				// uncomment when there is an actual player
				// raiseSlider.setMin(player.getBank());
				// raiseSlider.setMax(player.getBank());
			}
		});
		
		 raise.setOnAction(e -> {
			 raise(client,raiseSlider,player);
		 });
		 
		

	}
	
	
	/**
	 * Makes it possible to use keys to play, insted of mouse
	 * @param check
	 * @param call
	 * @param fold
	 * @param raise
	 * @param gui
	 * @param client
	 * @param raiseSlider
	 * @param player
	 */
	private static void setKeyListener(Button check, Button call, Button fold,
			Button raise, GUI gui, GUIClient client, Slider raiseSlider, Player player) {
		gui.scene.setOnKeyPressed( event -> 
		{
		
                switch (event.getCode()) {
                    case UP:    raise(client,raiseSlider,player); break;
                    case DOWN:  client.setAction(new Action.Check()); break;
                    case LEFT:  client.setAction(new Action.Call()); break;
                    case RIGHT: client.setAction(new Action.Fold()); break;                  
                }	
		     });
		
	}

	private static String checkMaxBid(Slider slider) {
		if (slider.getValue() == slider.getMax())
			return "GO ALL IN";
		else
			return (int) slider.getValue() + " CHIPS";
	}
	
	private static void raise(GUIClient client, Slider raiseSlider, Player player){
		if (client.getSession().getPlayerOptions(player).getMinRaise() < raiseSlider.getValue()) {
			 if(client.getSession().getPlayerOptions(player).getMaxRaise() > raiseSlider.getValue())
				 client.setAction(new Action.Raise((int) raiseSlider.getValue()));
			 }
	}

	private static void setHBoxSize(HBox hbox, GUI gui) {
		hbox.setMinHeight(gui.getHeight() * 0.055);
		hbox.setMaxHeight(gui.getHeight() * 0.055);
		// hbox.setMinHeight(70);
		// hbox.setMaxHeight(70);

	}

}
