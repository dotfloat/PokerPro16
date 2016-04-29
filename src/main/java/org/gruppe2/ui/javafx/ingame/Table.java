package org.gruppe2.ui.javafx.ingame;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import org.gruppe2.ui.Resources;
import org.gruppe2.ui.javafx.PokerApplication;
import org.gruppe2.ui.javafx.menu.HelperMenu;
import org.gruppe2.ui.javafx.menu.RightMenu;

public class Table extends StackPane {

	@FXML private ImageView pokerTable;
	@FXML private VBox tableItemsBox;
	@FXML public ChatBox chatBox;
	@FXML public ThisPlayerInfoBox thisPlayer;
	@FXML private RightMenu rightMenu;
	@FXML private HelperMenu helperMenu;
	@FXML public CommunityCards communityCardsBox;
	@FXML public Pot pot;
	@FXML private Label nothing;
	@FXML private Label nothing2;

	@SuppressWarnings("static-access")
	public Table() {
		Resources.loadFXML(this);
		pokerTable.fitWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.6));
		this.setAlignment(rightMenu, Pos.TOP_RIGHT);
		this.setAlignment(chatBox, Pos.BOTTOM_LEFT);
		this.setAlignment(thisPlayer, Pos.BOTTOM_CENTER);
		this.setAlignment(helperMenu, Pos.TOP_LEFT);
		sizeTableBox();
		
	}

	private void sizeTableBox() {
		double scale = 0.075;
		tableItemsBox.spacingProperty().bind(PokerApplication.getRoot().heightProperty().multiply(0.002));
		communityCardsBox.minHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(scale));
		communityCardsBox.maxHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(scale));

		nothing.minHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(scale));
		nothing.maxHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(scale));
		nothing2.minHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(scale));
		nothing2.maxHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(scale));
	}
	
	

}
