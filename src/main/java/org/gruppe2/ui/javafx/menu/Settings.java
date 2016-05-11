package org.gruppe2.ui.javafx.menu;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import org.gruppe2.Main;
import org.gruppe2.Resources;
import org.gruppe2.ui.UIResources;
import org.gruppe2.ui.javafx.Modal;

public class Settings extends VBox {

	@FXML
	TextField nameField;

	@FXML
	TilePane avatarTiles;

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
		Main.setProperty("name",nameField.getText());
	}

	private void getAvatars(){
		String[] avatars = Resources.listAvatars();

		for(String avatar: avatars){
			//System.out.println(avatar);
			Pane avatarPane = new Pane();
			avatarPane.setOnMouseClicked(frisk -> Main.setProperty("avatar",avatar));
			avatarPane.getChildren().add(new ImageView(UIResources.getAvatar(avatar)));
			avatarTiles.getChildren().add(avatarPane);
		}
	}
}
