package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import org.gruppe2.ui.Resources;

public class ChatBox extends ScrollPane {
	private int width = PokerApplication.getWidth();
	private int height = PokerApplication.getHeight();
	
	
    @FXML private TextArea textArea;
	
	
	public ChatBox() {
		Resources.loadFXML(this);
		setPositionsAndSettings();
	}




	private void setPositionsAndSettings() {
		
		this.setMaxSize(width*0.16, height*0.13);
		
		
		this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        textArea.setEditable(false);
		
	}
	
	public void setEventListeners(TextField textField) {
		
        textField.setOnAction(e -> { // Put text from textField to textArea
            if (textField.getText().equals(null) || textField.getText().equals("")) ;
            else {
                textArea.setText(textArea.getText() + "\n" + "General" + ": " + textField.getText());
                textField.setText("");
            }
        });
    }
}
