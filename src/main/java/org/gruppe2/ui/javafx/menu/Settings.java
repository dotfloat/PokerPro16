package org.gruppe2.ui.javafx.menu;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import javafx.util.Duration;
import org.gruppe2.Main;
import org.gruppe2.Resources;
import org.gruppe2.ui.UIResources;
import org.gruppe2.ui.javafx.Modal;
import org.gruppe2.ui.javafx.PokerApplication;

public class Settings extends VBox {

	@FXML
	TextField nameField;

	@FXML
	TilePane avatarTiles;

	private Pane highlitedPane;

	public Settings(){
		UIResources.loadFXML(this);
		getAvatars();
	}

	public static void show() {
		Modal modal = new Modal(true);
		modal.setPercentSize(0.5, 0.5);
		modal.setContent(new Settings());
		modal.show();
	}

	public void setName(){
		if(nameField.getText() != null && !nameField.getText().isEmpty())
		Main.setProperty("name" ,nameField.getText());
	}

	private void getAvatars(){
		String[] avatars = Resources.listAvatars();

		for(String avatar: avatars){

			Pane avatarPane = new Pane();
			ImageView imageView = new ImageView(UIResources.getAvatar(avatar));
			imageView.preserveRatioProperty().setValue(true);
			imageView.fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.05));


			avatarPane.setOnMouseClicked(frisk -> {Main.setProperty("avatar",avatar);
				flagTile(avatarPane);
			});

			avatarPane.getChildren().add(imageView);
			avatarTiles.getChildren().add(avatarPane);

		}
	}

	private void flagTile(Pane pane){
		if(highlitedPane!= null) {
			highlitedPane.setStyle("");
		}
		pane.setStyle("-fx-effect: dropshadow(gaussian, #0099ff, 5, 2, 0, 0);");
		highlitedPane = pane;
	}
}
