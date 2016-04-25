package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import org.gruppe2.ui.Resources;

public class Pot extends Label {

//	@FXML
//	private Label potLabel;

	
	public Pot() {
		Resources.loadFXML(this);
//		this.maxWidthProperty().bind(
//				PokerApplication.getRoot().widthProperty().multiply(0.2));
		
	}
	public void updatePot(int value){
		this.setText("Total Pot: "+String.valueOf(value)+" CHIPS");
	}
}
