package org.gruppe2.ui.javafx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.Region;

import org.gruppe2.game.calculation.GeneralCalculations;
import org.gruppe2.game.old.Player;
import org.gruppe2.ui.Resources;

public class ChatBox extends TextArea {
	Player player;
	
    public ChatBox() {
        Resources.loadFXML(this);
        setPositionsAndSettings();
		appendText("Welcome to PokerPro16. \n Type \"/help\" to get a list of valid commands.");
    }


    private void setPositionsAndSettings() {
        double chatScale = 0.22;
        this.prefWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(chatScale));
        this.prefHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(chatScale));
        this.maxWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(chatScale));
        this.maxHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(chatScale));

        editableProperty().setValue(false);
        setWrapText(true);
    }

    public void setEventListeners(TextField textField, Player player) {
    	this.player = player;
        textField.setOnAction(e -> { // Put text from textField to textArea
        	if (textField.getText().equals(null) || textField.getText().equals("")) setScrollTop(Double.MAX_VALUE);
        	
        	else if(checkForCommands(textField));
            
        	else{
	                this.appendText("\n" + player + ": " + textField.getText());
	            }
        	textField.setText("");
        });
        makeTransparent();
    }

    private void makeTransparent() {
    	this.skinProperty().addListener(new ChangeListener<Skin<?>>() {

            @Override
            public void changed(
              ObservableValue<? extends Skin<?>> ov, Skin<?> t, Skin<?> t1) {
                if (t1 != null && t1.getNode() instanceof Region) {
                    Region r = (Region) t1.getNode();
                    r.setBackground(Background.EMPTY);

                    r.getChildrenUnmodifiable().stream().
                            filter(n -> n instanceof Region).
                            map(n -> (Region) n).
                            forEach(n -> n.setBackground(Background.EMPTY));

                    r.getChildrenUnmodifiable().stream().
                            filter(n -> n instanceof Control).
                            map(n -> (Control) n).
                            forEach(c -> c.skinProperty().addListener(this)); // *
                }
            }
        });
		
	}


	/**
     * Method for doing commands
     * @param textField
     */
	private boolean checkForCommands(TextField textField) {
		String command = textField.getText().toLowerCase();
		if(command.equals("/besthand")){
			 
			String answer = GeneralCalculations.getBestHandForPlayer(((GameWindow)this.getParent().getParent()).communityCardsBox.getCommunityCards(), player).toString();
			this.appendText("\n" + player + "s possible best hand is: " + answer);
			return true;
		}
		else if(command.equals("/log")){
			this.appendText("\n" + player + ": " + textField.getText()+"is epic");
			//Print logs--->
			return true;
		}
		else if(command.equals("/fuckoff")){
			this.appendText("\n" + player + ": " + textField.getText()+"is epic");
			//Print playFuckOfClip--->
			return true;
		}
		else if(command.equals("/raidingparty")){
			this.appendText("\n" + player + ": " + textField.getText()+"is epic");
			//Print raidingPartyClip--->
			return true;
		}
		else if(command.equals("/clear")){
			this.setText("");
			return true;
		}
		else if(command.equals("/help")) {
			this.appendText("\n" + "Available commands: " + "\n /besthand - shows best possible hand \n" +
					"/log - shows gamelog \n" +
					"/clear - clears the chat window \n" +
					"/help - shows available commands");
			return true;
		}
		else
			return false;
	} 
	public void postMessage(String message){
		this.appendText(message);
	}
}
