package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import org.gruppe2.ui.Resources;

public class Table extends StackPane {
	private int width = PokerApplication.getWidth();
	private int height = PokerApplication.getHeight();
	
	@FXML private ImageView pokerTable;
	@FXML private ChatBox chatBox;
	
	public Table() {
		Resources.loadFXML(this);
		pokerTable.fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.6));
		this.setAlignment(chatBox, Pos.BOTTOM_LEFT);
		
	}
}
