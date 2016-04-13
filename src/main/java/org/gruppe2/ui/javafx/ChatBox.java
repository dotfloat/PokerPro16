package org.gruppe2.ui.javafx;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.gruppe2.game.calculation.GeneralCalculations;
import org.gruppe2.game.old.Player;
import org.gruppe2.ui.Resources;

public class ChatBox extends TextArea {
	Player player;
	
    public ChatBox() {
        Resources.loadFXML(this);
        setPositionsAndSettings();
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
	                this.setText(this.getText() + "\n" + player + ": " + textField.getText());
	                this.setScrollTop(Double.MAX_VALUE);
	            }
        	textField.setText("");
        });
        
    }

    /**
     * Method for doing commands
     * @param textField
     */
	private boolean checkForCommands(TextField textField) {
		String command = textField.getText();
		if(command.equals("bestHand")){
			 
			String answer = GeneralCalculations.getBestHandForPlayer(((GameWindow)this.getParent().getParent()).communityCardsBox.getCommunityCards(), player).toString();
			this.setText(this.getText() + "\n" + player + "s possible best hand is: " + answer);
			return true;
		}
		else if(command.equals("log")){
			this.setText(this.getText() + "\n" + player + ": " + textField.getText()+"is epic");
			//Print logs--->
			return true;
		}
		else if(command.equals("fuck off")){
			this.setText(this.getText() + "\n" + player + ": " + textField.getText()+"is epic");
			//Print playFuckOfClip--->
			return true;
		}
		else if(command.equals("raiding party")){
			this.setText(this.getText() + "\n" + player + ": " + textField.getText()+"is epic");
			//Print raidingPartyClip--->
			return true;
		}
		else
			return false;
	} 
}
