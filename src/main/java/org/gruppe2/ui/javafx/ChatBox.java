package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.gruppe2.game.old.Player;
import org.gruppe2.ui.Resources;

public class ChatBox extends ScrollPane {
	private int width = PokerApplication.getWidth();
	private int height = PokerApplication.getHeight();
	
	private Player player;
	
    @FXML private TextArea textArea;
	
	
	public ChatBox() {
		Resources.loadFXML(this);
		setPositionsAndSettings();
		
	}




	private void setPositionsAndSettings() {
		
		this.setMaxSize(width*0.10, height*0.10);
		this.setLayoutX(width * 0.01);
		this.setLayoutY(height*0.80);
		
		this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        textArea.setEditable(false);
		
	}
	
	public void setEventListeners(TextField textField) {
		
		//		if(textField == null){
//			System.out.println("Textfield is not initialized");
//			System.exit(1);
//		}
		
        textField.setOnAction(e -> { // Put text from textField to textArea
            if (textField.getText().equals(null) || textField.getText().equals("")) ;
            else {
                textArea.setText(textArea.getText() + "\n" + "General" + ": " + textField.getText());
                textField.setText("");
            }
        });

//        this.heightProperty().addListener((observable, oldVal, newVal) -> {
//            this.setVvalue(((Double) newVal).doubleValue());
//        });
    }
}
