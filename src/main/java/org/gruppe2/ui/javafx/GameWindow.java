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
	GUIClient client;
	PossibleActions pa;
	Player player;
	
	@FXML
	private Button FOLD;
	@FXML 
	private Slider raiseSlider;
	@FXML
	private Label sliderValue;
//	@FXML
//	private ChatBox chatBox;
	
	public GameWindow(Player player){
		super();
		this.player = player;
		this.client = (GUIClient) player.getClient();
		pa = player.getClient().getSession()
	                .getPlayerOptions(player);
	}

	
	void foldAction(){
		client.setAction(new Action.Fold());
	}
	
	void betAction(){
		 if (pa.canCall() && pa.canRaise() && raiseSlider.getValue() > 1)
             raise(client, raiseSlider, player);
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
