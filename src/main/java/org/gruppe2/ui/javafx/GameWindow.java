package org.gruppe2.ui.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import org.gruppe2.game.old.Action;
import org.gruppe2.game.old.Player;
import org.gruppe2.game.old.PossibleActions;
import org.gruppe2.ui.old_javafx.GUIClient;

/**
 * Created by kjors on 04.04.2016.
 */
public class GameWindow implements Initializable {
	private int width = PokerApplication.getWidth();
	private int height = PokerApplication.getHeight();
	
	GUIClient client;
	PossibleActions pa;
	Player player;
	@FXML private BorderPane borderPane;
	@FXML private TextField chatField;
	@FXML private ImageView pokerTable;
	@FXML private Button FOLD;
	@FXML private Slider slider;
	@FXML private Label sliderValue;
	@FXML private Button BET;
	
	

	@FXML
	private void setSizes() {
		slider.prefWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.4));
        slider.setMinWidth(height * 0.23);
        slider.setMax(5000);
       
        sliderValue.setMinWidth(width * 0.09);
        sliderValue.setMaxWidth(height * 0.09);

		pokerTable.fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.7));
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		assert(slider != null):"slider is null";
		setSizes();
		setEvents();
	}
	/**
     * If raise is all you have, change text of raise button to ALL IN
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
