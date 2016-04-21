package org.gruppe2.ui.javafx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import org.gruppe2.game.old.GameBuilderAiDifficultyOptions;
import org.gruppe2.ui.Resources;

public class Settings extends StackPane {

	@FXML
	private TextField name;
	@FXML
	private TextField small;
	@FXML
	private TextField big;
	@FXML
	private TextField startValue;
	@FXML
	private TextField avatar;
	@FXML
	private TextField numberBots;
	@FXML
	private Button botButton;
	@FXML
	private Label nameLabel;
	@FXML
	private Label smallLabel;
	@FXML
	private Label bigLabel;
	@FXML
	private Label startValueLabel;
	@FXML
	private Label avatarLabel;
	@FXML
	private Label numberBotsLabel;

	public Settings() {
		Resources.loadFXML(this);
		loadSettings();
	}

	private void loadSettings() {
		if (PokerApplication.diff == GameBuilderAiDifficultyOptions.RANDOM)
			botButton.setText("BOT IS EASY");
		else if (PokerApplication.diff == GameBuilderAiDifficultyOptions.MIXED)
			botButton.setText("Bot is MIXED");
		else
			botButton.setText("Bot is HARD");
		ArrayList<String> savedSettings = loadSettingsList();

		if (savedSettings.size() == 6) {
			nameLabel.setText(savedSettings.get(0));
			PokerApplication.name = nameLabel.getText();
			smallLabel.setText(savedSettings.get(1));
			PokerApplication.small = Integer.valueOf(smallLabel.getText());
			bigLabel.setText(savedSettings.get(2));
			PokerApplication.big = Integer.valueOf(bigLabel.getText());
			startValueLabel.setText(savedSettings.get(3));
			PokerApplication.bank = Integer.valueOf(startValueLabel.getText());
			avatarLabel.setText(savedSettings.get(4));
			numberBotsLabel.setText(savedSettings.get(5));
			if (numberBotsLabel.getText().matches("[0-9]+")
					&& numberBotsLabel.getText().length() > 0
					&& Integer.valueOf(numberBotsLabel.getText()) < 10) {
				int numBots = Integer.valueOf(numberBotsLabel.getText());

				if (numBots < 3)
					numBots = 3;
				else if (numBots > 9)
					numBots = 9;

				PokerApplication.numberOfBots = numBots;
			}
		}
	}

	public void ok() {
		setName();
		setSmallBlind();
		setBigBlind();
		setStartMoney();
		setAvatar();
		numberOfBots();
		saveSettings();
		SceneController.setScene(new MainMenu());
	}

	public void cancel() {
		SceneController.setScene(new MainMenu());
	}

	public void setName() {
		if (validName()) {
			PokerApplication.name = name.getText();
		}
	}

	public void setSmallBlind() {
		if (validSmall()) {
			PokerApplication.small = Integer.valueOf(small.getText());
		}
	}

	public void setBigBlind() {
		if (validBig()) {
			PokerApplication.big = Integer.valueOf(big.getText());
		}
	}

	public void setStartMoney() {
		if (validStartValue()) {
			PokerApplication.bank = Integer.valueOf(startValue.getText());
		}
	}

	public void setAvatar() {
		if (validAvatar()) {
			System.out.println("Not yet implemented");
			// TODO
		}

	}

	public void numberOfBots() {
		if (validNumberBots()) {
			int numBots = Integer.valueOf(numberBots.getText());

			if (numBots < 3)
				numBots = 3;
			else if (numBots > 9)
				numBots = 9;

			PokerApplication.numberOfBots = numBots;
		}

	}

	public void setBotDificulty() {
		if (botButton.getText().equals("BOT IS EASY")) {

			PokerApplication.diff = GameBuilderAiDifficultyOptions.MIXED;
			botButton.setText("Bot is MIXED");
		} else if (botButton.getText().equals("Bot is MIXED")) {
			PokerApplication.diff = GameBuilderAiDifficultyOptions.ADVANCED;
			botButton.setText("Bot is HARD");
		} else if (botButton.getText().equals("Bot is HARD")) {
			PokerApplication.diff = GameBuilderAiDifficultyOptions.RANDOM;
			botButton.setText("BOT IS EASY");
		}
	}
	
	

	private void saveSettings() {
		File file = new File(
				"src/main/resources/saved_files/settings/standardSettings.txt");
		file.delete();
		Scanner scanner = null;
		int lineNumber = 0;

		File fnew = new File(
				"src/main/resources/saved_files/settings/standardSettings.txt");

		try {
			FileWriter newSaveWriter = new FileWriter(fnew, false);
			ArrayList<String> newSettingsList = newSettingsList();
			while (lineNumber < 6) {
				newSaveWriter.write(newSettingsList.get(lineNumber)+"\n");
				lineNumber++;
			}

			newSaveWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				/* Proccess line */

			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		scanner.close();
	}
	private ArrayList<String> newSettingsList() {
		ArrayList<String> newSettings = new ArrayList<String>();
		if(validName())
			newSettings.add(name.getText());
		else
			newSettings.add(PokerApplication.name);
		if(validSmall())
			newSettings.add(small.getText());
		else
			newSettings.add(String.valueOf(PokerApplication.small));
		if(validBig())
			newSettings.add(big.getText());
		else
			newSettings.add(String.valueOf(PokerApplication.big));
		if(validStartValue())
			newSettings.add(startValue.getText());
		else
			newSettings.add(String.valueOf(PokerApplication.bank));
		if(validAvatar())
			newSettings.add(avatar.getText());
		else
			newSettings.add("Standard");
		if(validNumberBots())
			newSettings.add(numberBots.getText());
		else
			newSettings.add(String.valueOf(PokerApplication.numberOfBots));
		return newSettings;
	}
	
	private ArrayList<String> loadSettingsList() {
		ArrayList<String> savedSettings = new ArrayList<String>();
		try {

			BufferedReader brq = new BufferedReader(
					new FileReader(
							"src/main/resources/saved_files/settings/standardSettings.txt"));

			String currentLine;

			while ((currentLine = brq.readLine()) != null) {
				savedSettings.add(currentLine);
			}
			brq.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return savedSettings;
	}

	private boolean validName() {
		return !name.getText().equals("")
				&& !name.getText().equals("Set player Name");
	}

	private boolean validSmall() {
		return small.getText().matches("[0-9]+")
				&& small.getText().length() > 1;
	}

	private boolean validBig() {
		return big.getText().matches("[0-9]+") && big.getText().length() > 1;
	}

	private boolean validStartValue() {
		return startValue.getText().matches("[0-9]+")
				&& startValue.getText().length() > 1;
	}

	private boolean validAvatar() {
		return avatar.getText().contains(".png");
	}

	private boolean validNumberBots() {
		return numberBots.getText().matches("[0-9]+")
				&& numberBots.getText().length() > 0
				&& Integer.valueOf(numberBots.getText()) < 10;
	}
}
