package org.gruppe2.ui.javafx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import org.gruppe2.game.old.Action;
import org.gruppe2.game.old.Player;
import org.gruppe2.game.old.PossibleActions;
import org.gruppe2.ui.Resources;

public class ChoiceBar extends HBox {
	private int width = PokerApplication.getWidth();
	private int height = PokerApplication.getHeight();
	private GUIPlayer client;
	private Player player;
	@FXML private TextField chatField;
	@FXML private Button FOLD;
	@FXML private Slider slider;
	@FXML private Label sliderValue;
	@FXML private Button BET;
	
	public ChoiceBar() {
		Resources.loadFXML(this);
		setSizes();
	}
	
	@FXML
	private void setSizes() {
		slider.prefWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.3));
        slider.setMinWidth(width * 0.15);
        slider.setMax(5000);
       
        sliderValue.setMinWidth(width * 0.09);
        sliderValue.setMaxWidth(height * 0.09);
	}
	
	@FXML
	public void setEvents(GUIPlayer client, Player player) {
		this.client = client;
		this.player = player;
		FOLD.setOnAction(e -> foldAction());
		BET.setOnAction(e -> betAction());
		slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                sliderValue.textProperty().setValue(checkMaxBid(slider));
            }
        });
	}
	/**
	 * This will become fxml
	 */
	private void foldAction(){
		client.setAction(new Action.Fold());
	}
	/**
	 * This will become fxml
	 */
	private void betAction(){
		PossibleActions pa = client.getSession()
                .getPlayerOptions(player);

		 if ( pa.canRaise() && slider.getValue() > 1)
             raise(client, slider, player);
         else if (pa.canCall())
             client.setAction(new Action.Call());
         else if(pa.canCheck())
             client.setAction(new Action.Check());
	}
	
	private void raise(GUIPlayer client, Slider raiseSlider, Player player) {
        if (client.getSession().getPlayerOptions(player).getMinRaise() <= raiseSlider
                .getValue()) {
            if (client.getSession().getPlayerOptions(player).getMaxRaise() >= raiseSlider
                    .getValue())
                client.setAction(new Action.Raise((int) raiseSlider.getValue()));
        }
    }
	/**
     * If you raise all you have, change text of raise button to ALL IN
     *
     * @param slider
     * @return
     */
    private String checkMaxBid(Slider slider) {
        if (slider.getValue() == slider.getMax()) BET.setText("ALL IN");
        else BET.setText("RAISE");
        return (int) slider.getValue() + " CHIPS";
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
        if( pa.canCall())
        	BET.setText("Call");
   
        if (pa.canRaise() && slider.getValue() > 1) {
            BET.getStyleClass().add("button");
            BET.setText("RAISE");
        }
        else  if(pa.canCheck())
        	BET.setText("Check");
        
        slider.setMax(pa.getMaxRaise());
        slider.setMin(pa.getMinRaise());
        slider.setValue(pa.getMinRaise());
    }
}
