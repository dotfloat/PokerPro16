package org.gruppe2.frontend;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

import org.gruppe2.backend.Action;
import org.gruppe2.backend.Player;
import org.gruppe2.backend.PossibleActions;

/**
 * This is the bottom buttons, textfields and slider.
 * 
 * @author htj063
 *
 */
public class ChoiceBar {
	boolean canCheck = true;
	boolean canCall = true;
	boolean canRaise = true;
	boolean canFold = true;
	
	Button check;
	Button call; 
	Button fold; 
	Button raise;
	Slider raiseSlider;

	public void showChoices(GUI gui, Player player) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				HBox hbox = new HBox(gui.getWidth() * 0.01);
				hbox.getStyleClass().add("hbox");
				createHBox(gui, hbox, player);
			}
		});
	}
	/**
	 * Creates the boxes on bottom you can push, call, check etc. 
	 * @param gui
	 * @param hbox
	 * @param player
	 */
	private void createHBox(GUI gui, HBox hbox, Player player) {

		check = new Button("CHECK");
		call = new Button("CALL");
		fold = new Button("FOLD");
		raise = new Button("RAISE");

		raiseSlider = new Slider(25, 5000, 50);
		raiseSlider.setMaxWidth(gui.getWidth() * 0.23);
		raiseSlider.setMinWidth(gui.getWidth() * 0.23);
		Label sliderValue = new Label((int) raiseSlider.getValue() + " CHIPS");
		sliderValue.setMinWidth(gui.getWidth() * 0.08);
		sliderValue.setMaxWidth(gui.getWidth() * 0.08);

		Label showCards = new Label(""); // Label that creates space, does
											// nothing else
		showCards.setMinWidth(gui.getWidth() * 0.25);
		showCards.setMaxWidth(gui.getWidth() * 0.25);

		setButtonAction(raiseSlider, check, call, raise, fold, player,
				sliderValue, gui.getClient());

		hbox.getChildren().addAll(fold, call, check, raiseSlider, sliderValue, raise, showCards);
		setHBoxSize(hbox, gui);

		setKeyListener(check, call, fold, raise, gui, gui.getClient(), raiseSlider, player);

		gui.border.setBottom(hbox);
	}
	/**
	 * Action events for bottom buttons
	 * @param raiseSlider
	 * @param check
	 * @param call
	 * @param raise
	 * @param fold
	 * @param player
	 * @param sliderValue
	 * @param client
	 */
	private void setButtonAction(Slider raiseSlider, Button check, Button call,
			Button raise, Button fold, Player player, Label sliderValue,
			GUIClient client) {
		
		
		check.setOnAction(e -> {
			if (canCheckNow()){
				client.setAction(new Action.Check()); 
			}
		});

		call.setOnAction(e -> {
			if (canCallNow())
				client.setAction(new Action.Call());
		});

		fold.setOnAction(e -> {
			if (canFoldNow())
				client.setAction(new Action.Fold());
		});

		// Slider
		raiseSlider.setMajorTickUnit(10);
		raiseSlider.setBlockIncrement(10);
		raiseSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				sliderValue.textProperty().setValue(checkMaxBid(raiseSlider));
			}
		});
		
		raise.setOnAction(e -> {
			if (canRaiseNow())
				raise(client, raiseSlider, player);
		});

	}

	/**
	 * Makes it possible to use keys to play, instead of mouse
	 * 
	 */
	@SuppressWarnings("incomplete-switch")
	private void setKeyListener(Button check, Button call, Button fold,
			Button raise, GUI gui, GUIClient client, Slider raiseSlider,
			Player player) {
		
		gui.scene.setOnKeyPressed(event -> {

			switch (event.getCode()) {
			case UP:
				if(canRaiseNow())
					raiseSlider.setValue(raiseSlider.getValue()*2);		
				break;
			case DOWN:
				raiseSlider.setValue(raiseSlider.getValue()/2);
				break;
			case LEFT:
				client.setAction(new Action.Fold());
				break;
			case RIGHT:
				if(canCall && canRaise && raiseSlider.getValue() > 1)
					raise(client, raiseSlider, player);
				else if(canCall)
					client.setAction(new Action.Call());
				else if(canCheck)
					client.setAction(new Action.Check());
				
				break;
			}
		});
	}
	/**
	 * If raise is all you have, change text of raise button to ALL IN
	 * @param slider
	 * @return
	 */
	private String checkMaxBid(Slider slider) {
		if (slider.getValue() == slider.getMax()) raise.setText("ALL IN");
		else raise.setText("RAISE");
		return (int) slider.getValue() + " CHIPS";
	}

	private void raise(GUIClient client, Slider raiseSlider, Player player) {
		if (client.getSession().getPlayerOptions(player).getMinRaise() <= raiseSlider
				.getValue()) {
			if (client.getSession().getPlayerOptions(player).getMaxRaise() >= raiseSlider
					.getValue())
				client.setAction(new Action.Raise((int) raiseSlider.getValue()));
		}
	}

	private void setHBoxSize(HBox hbox, GUI gui) {
		hbox.setMinHeight(gui.getHeight() * 0.07);
		hbox.setMaxHeight(gui.getHeight() * 0.07);
	}

	/**
	 * Removes eventpossibilities and makes buttons grey, if they are not
	 * possible to click.
	 * 
	 * @param player
	 */
	public void updatePossibleBarsToClick(Player player) {
		PossibleActions pa = player.getClient().getSession()
				.getPlayerOptions(player);
//		System.out.println("Possible actions: canCall: "+pa.canCall()+" canCheck: "+pa.canCheck());
		
		if (pa.canCall()) {
			call.getStyleClass().add("button");
			canCall = true;

		} else {
			call.getStyleClass().add("buttonIllegal");
			canCall = false;
		}
		if (pa.canCheck()) {
			check.getStyleClass().add("button");
			canCheck = true;
		} else {
			check.getStyleClass().add("buttonIllegal");
			canCheck = false;
		}
		if (pa.canRaise()) {
			raise.getStyleClass().add("button");
			canRaise = true;
		} else {
			raise.getStyleClass().add("buttonIllegal");
			canRaise = false;
		}
		raiseSlider.setMax(pa.getMaxRaise());
		raiseSlider.setMin(pa.getMinRaise());
		raiseSlider.setValue(pa.getMinRaise());
	}
	
	private boolean canCheckNow(){
		return canCheck;
	}
	private boolean canCallNow(){
		return canCall;
	}
	private boolean canRaiseNow(){
		return canRaise;
	}
	private boolean canFoldNow(){
		return canFold;
	}

}
