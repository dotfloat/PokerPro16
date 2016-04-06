package org.gruppe2.ui.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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
	@FXML
	private BorderPane borderPane;
	@FXML
	private TextField chatField;
	@FXML
	private Button FOLD;
	@FXML
	private Slider slider;
	@FXML
	private Label sliderValue;
	@FXML
	private Button BET;
	
	public GameWindow(Player player){
		super();
		this.player = player;
		this.client = (GUIClient) player.getClient();
		pa = player.getClient().getSession()
				.getPlayerOptions(player);
		setEvents();
	}
	public GameWindow(){
		
		setSizes();
//		setEvents();
	}

	@FXML
	private void setSizes() {
        
		slider.setMaxWidth(width * 0.23);
        slider.setMinWidth(height * 0.23);
       
        sliderValue.setMinWidth(width * 0.08);
        sliderValue.setMaxWidth(height * 0.08);
		
	}
	@FXML
	private void setEvents() {
		FOLD.setOnAction(e -> foldAction());
		BET.setOnAction(e -> betAction());
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
		 if (pa.canCall() && pa.canRaise() && slider.getValue() > 1)
             raise(client, slider, player);
         else if (pa.canCall())
             client.setAction(new Action.Call());
         else if(pa.canCheck())
             client.setAction(new Action.Check());
	}
	
	private void raise(GUIClient client, Slider raiseSlider, Player player) {
        if (client.getSession().getPlayerOptions(player).getMinRaise() <= raiseSlider
                .getValue()) {
            if (client.getSession().getPlayerOptions(player).getMaxRaise() >= raiseSlider
                    .getValue())
                client.setAction(new Action.Raise((int) raiseSlider.getValue()));
        }
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("he");
		System.out.println(slider);
		assert(slider != null):"slider is null";
	}
}
