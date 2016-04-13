package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import javafx.scene.layout.VBox;
import org.gruppe2.game.old.GameSession;
import org.gruppe2.ui.Resources;

public class Table extends StackPane {

	@FXML private ImageView pokerTable;
	@FXML private VBox tableItemsBox;
	@FXML public ChatBox chatBox;
	@FXML private RightMenu rightMenu;
	@FXML public CommunityCards communityCardsBox;
	@FXML private Pot pot;
	@FXML private Label nothing;
	@FXML private Label nothing2;

	@SuppressWarnings("static-access")
	public Table() {
		Resources.loadFXML(this);
		pokerTable.fitWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.6));
		this.setAlignment(rightMenu, Pos.TOP_RIGHT);
		this.setAlignment(chatBox, Pos.BOTTOM_LEFT);
		sizeTableBox();
		//when pot gets updates, remove the line below
		pot.visibleProperty().setValue(false);
	}

	private void sizeTableBox() {
		double scale = 0.075;
		tableItemsBox.spacingProperty().bind(PokerApplication.getRoot().heightProperty().multiply(0.002));
		communityCardsBox.minHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(scale));
		communityCardsBox.maxHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(scale));
		pot.minHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(scale));
		pot.maxHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(scale));
		nothing.minHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(scale));
		nothing.maxHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(scale));
		nothing2.minHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(scale));
		nothing2.maxHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(scale));

	}

}
