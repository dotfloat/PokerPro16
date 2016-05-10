package org.gruppe2.ui.javafx.menu;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import org.gruppe2.network.MasterClient;
import org.gruppe2.ui.UIResources;

public class CreateGameSettings extends VBox {
	MasterClient masterClient;
	@FXML
	TextField tableName;
	@FXML
	TextField smallBlind;
	@FXML
	TextField bigBlind;
	@FXML
	TextField startMoney;
	@FXML
	TextField maxPlayers;

	public CreateGameSettings(MasterClient masterClient) {
		UIResources.loadFXML(this);
		this.masterClient = masterClient;

	}

	@FXML
	private void ok() {
		System.out.println("ok clicked");
		if (valuesAreValid()) {
			masterClient.requestCreateGame();
		}

	}

	private boolean valuesAreValid() {
		if (!tableName.getText().equals("") && !smallBlind.getText().equals("")
				&& !bigBlind.getText().equals("")
				&& !startMoney.getText().equals("")
				&& !maxPlayers.getText().equals("")) {
			return true;
		} else
			return false;
	}
}
