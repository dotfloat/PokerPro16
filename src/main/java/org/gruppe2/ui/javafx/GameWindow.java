package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import org.gruppe2.game.old.Action;
import org.gruppe2.game.old.Player;
import org.gruppe2.game.old.PossibleActions;
import org.gruppe2.ui.old_javafx.ChatBox;
import org.gruppe2.ui.old_javafx.GUIClient;

/**
 * Created by kjors on 04.04.2016.
 */
public class GameWindow extends BorderPaneController {
	private int width = PokerApplication.getWidth();
	private int height = PokerApplication.getHeight();
	
	GUIClient client;
	PossibleActions pa;
	Player player;
	@FXML
	private ChatBox chatBox;
	@FXML
	private Button FOLD;
	@FXML
	private Slider slider = new Slider();
	@FXML
	private Label sliderValue = new Label();
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
		super();
		setSizes();
//		setEvents();
	}


	private void setSizes() {
        
		slider.setMaxWidth(width * 0.50);
        slider.setMinWidth(height * 0.50);
        sliderValue.setMinWidth(width * 0.08);
        sliderValue.setMaxWidth(height * 0.08);
		
	}
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
}
