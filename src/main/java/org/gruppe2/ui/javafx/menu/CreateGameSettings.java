package org.gruppe2.ui.javafx.menu;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import org.gruppe2.Main;
import org.gruppe2.Resources;
import org.gruppe2.network.MasterClient;
import org.gruppe2.ui.UIResources;
import org.gruppe2.ui.javafx.Modal;

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
	@FXML
	TextField minPlayers;
	@FXML
	ComboBox<String> botDiff;

	public CreateGameSettings(MasterClient masterClient) {
		UIResources.loadFXML(this);
		this.masterClient = masterClient;
		setDefaultSettings();

	}

	@FXML
	private void ok() {
		if (valuesAreValid()) {
			saveSettings();
			masterClient.requestCreateGame(tableName.getText(),
					smallBlind.getText(), bigBlind.getText(),
					startMoney.getText(), maxPlayers.getText(),
					minPlayers.getText(),botDiff.getSelectionModel()
					.getSelectedItem()
					);
		}
	}

	private void saveSettings() {
		Main.setProperty("tableName", tableName.getText());
		Main.setProperty("smallBlind", smallBlind.getText());
		Main.setProperty("bigBlind", bigBlind.getText());
		Main.setProperty("startMoney", startMoney.getText());
		Main.setProperty("minPlayers", minPlayers.getText());
		Main.setProperty("maxPlayers", maxPlayers.getText());
		Main.setProperty("botDiff", botDiff.getSelectionModel()
				.getSelectedItem());
	}

	@FXML
	private void cancel() {
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

	public static void show(MasterClient masterClient) {
		Modal modal = new Modal(true);
		modal.setPercentSize(0.5, 0.5);
		modal.setContent(new CreateGameSettings(masterClient));
		modal.show();
	}

	public void setDefaultSettings() {
		tableName.setText(Main.getProperty("tableName"));
		smallBlind.setText(Main.getProperty("smallBlind"));
		bigBlind.setText(Main.getProperty("bigBlind"));
		startMoney.setText(Main.getProperty("startMoney"));
		maxPlayers.setText(Main.getProperty("maxPlayers"));
		minPlayers.setText(Main.getProperty("minPlayers"));
		setBotDiff();

	}

	private void setBotDiff() {

		botDiff.getItems().add("Easy");
		botDiff.getItems().add("Normal");
		botDiff.getItems().add("Hard");

		String botDiffFromFile = Main.getProperty("botDiff");

		botDiff.getSelectionModel().select(botDiffFromFile);
		// if(botDiffFromFile.equals("Easy")){
		// botDiff.getSelectionModel().select(0);
		// }
		// else if(botDiffFromFile.equals("Normal")){
		// botDiff.getSelectionModel().select(1);
		// }
		// else if(botDiffFromFile.equals("Hard")){
		// botDiff.getSelectionModel().select(2);
		// }

	}
}
