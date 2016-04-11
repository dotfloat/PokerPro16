package org.gruppe2.ui.javafx;

import org.gruppe2.ui.objects.Player;
import org.gruppe2.ui.Resources;
import org.gruppe2.ui.old_javafx.GUIClient;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ChoiceBar extends HBox {
	private int width = PokerApplication.getWidth();
	private int height = PokerApplication.getHeight();
	
	
	@FXML private TextField chatField;
	@FXML private Button FOLD;
	@FXML private Slider slider;
	@FXML private Label sliderValue;
	@FXML private Button BET;
	
	public ChoiceBar() {
		Resources.loadFXML(this);
		setSizes();
		setEvents();
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
	private void setEvents() {
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
		System.out.println("FOLD NOT IMPLEMENTED");
	}
	/**
	 * This will become fxml
	 */
	private void betAction(){
		System.out.println("BET NOT IMPLEMENTED");
//		 if (pa.canCall() && pa.canRaise() && slider.getValue() > 1)
//             raise(client, slider, player);
//         else if (pa.canCall())
//             client.setAction(new Action.Call());
//         else if(pa.canCheck())
//             client.setAction(new Action.Check());
	}
	
	private void raise(GUIClient client, Slider raiseSlider, Player player) {
//        if (client.getSession().getPlayerOptions(player).getMinRaise() <= raiseSlider
//                .getValue()) {
//            if (client.getSession().getPlayerOptions(player).getMaxRaise() >= raiseSlider
//                    .getValue())
//                client.setAction(new Action.Raise((int) raiseSlider.getValue()));
//        }
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
}
