package org.gruppe2.ui.javafx;

import javafx.scene.control.ScrollPane;

import org.gruppe2.ui.Resources;

public class ChatBox extends ScrollPane {
	private int width = PokerApplication.getWidth();
	private int height = PokerApplication.getHeight();
	
	
	
	
	public ChatBox() {
		Resources.loadFXML(this);
		this.setLayoutX(width*0.03);
		this.setLayoutY(height*0.90);
		PokerApplication.getRoot().getChildren().add(this);
	}
}
