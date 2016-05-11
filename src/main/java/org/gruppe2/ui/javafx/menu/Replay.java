package org.gruppe2.ui.javafx.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

import org.gruppe2.ui.UIResources;
import org.gruppe2.ui.javafx.Modal;

public class Replay extends VBox {
	
	@FXML
	private ComboBox<String>replays;
	@FXML
	private Button start;
	
	public Replay(){
		UIResources.loadFXML(this);
		loadReplaysToComboBox();
	}
	
	private void loadReplaysToComboBox() {
		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			        "Replay 1",
			        "Replay 2",
			        "Replay 3"
			    );

		ComboBox<String> combo = new ComboBox<String>(options);
		replays = combo;
		getChildren().add(combo);
	}

	@FXML
	private void startReplay(){
		
	}
	
	public static void show() {
        Modal modal = new Modal();
        modal.setPercentSize(0.8, 0.8);
        modal.setContent(new Replay());
        modal.setTitle("Replay");
        modal.show();
    }

}
