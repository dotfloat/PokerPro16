package org.gruppe2.ui.javafx;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

    public void setEventListeners(TextField textField) {

        textField.setOnAction(e -> { // Put text from textField to textArea
            checkForCommands(textField);
        	if (textField.getText().equals(null) || textField.getText().equals("")) setScrollTop(Double.MAX_VALUE);
            else {
                this.setText(this.getText() + "\n" + player + ": " + textField.getText());
                textField.setText("");
                this.setScrollTop(Double.MAX_VALUE);
            }
        });
    }


	private void checkForCommands(TextField textField) {
		String command = textField.getText();
		if(command.equals("bestHand")){
			 this.setText(this.getText() + "\n" + player + ": " + textField.getText()+"is epic");
			//Best hand--->
		}
		else if(command.equals("log")){
			this.setText(this.getText() + "\n" + player + ": " + textField.getText()+"is epic");
			//Print logs--->
		}
		
	} 

}
