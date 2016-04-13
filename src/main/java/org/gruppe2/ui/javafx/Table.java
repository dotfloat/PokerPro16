package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import org.gruppe2.ui.Resources;

public class Table extends StackPane {

	@FXML
	private ImageView pokerTable;
	@FXML
	public ChatBox chatBox;
	@FXML
	private RightMenu rightMenu;
	@FXML
	public CommunityCards communityCardsBox;
	

	@SuppressWarnings("static-access")
	public Table() {
		Resources.loadFXML(this);
		pokerTable.fitWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.6));
		this.setAlignment(rightMenu, Pos.TOP_RIGHT);
		this.setAlignment(chatBox, Pos.BOTTOM_LEFT);
	}
}
