package org.gruppe2.ui.javafx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import org.gruppe2.game.old.GameBuilderAiDifficultyOptions;
import org.gruppe2.ui.Resources;

public class Settings extends StackPane {
	
	@FXML private TextField name;
	@FXML private TextField  small;	
	@FXML private TextField  big; 
	@FXML private TextField  startValue;
	@FXML private TextField  avatar;
	@FXML private TextField  numberBots;
	@FXML private Button botButton;
	@FXML private Label nameLabel;
	@FXML private Label smallLabel;
	@FXML private Label bigLabel;
	@FXML private Label startValueLabel;
	@FXML private Label avatarLabel;
	@FXML private Label numberBotsLabel;
	
	public Settings() {
		Resources.loadFXML(this);
		loadSettings();
	}
	
	
	

	private void loadSettings() {
		if(PokerApplication.diff == GameBuilderAiDifficultyOptions.RANDOM)
			botButton.setText("BOT IS EASY");
		else if(PokerApplication.diff == GameBuilderAiDifficultyOptions.MIXED)
			botButton.setText("Bot is MIXED");
		else
			botButton.setText("Bot is HARD");
		ArrayList<String> savedSettings = getSavedSettingsList();
		
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
		if(numberBotsLabel.getText().matches("[0-9]+") && numberBotsLabel.getText().length() > 0 && Integer.valueOf(numberBotsLabel.getText()) < 10) {
			int numBots = Integer.valueOf(numberBotsLabel.getText());

			if (numBots < 3)
				numBots = 3;
			else if (numBots > 9)
				numBots = 9;

			PokerApplication.numberOfBots = numBots;
		}
	}




	private ArrayList<String> getSavedSettingsList() {
		ArrayList<String> savedSettings = new ArrayList<String>();
		try {
			
			BufferedReader brq = new BufferedReader(new FileReader("src/main/resources/saved_files/settings/standardSettings.txt"));
			
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




	public void ok() {
		setName();
		setSmallBlind();
		setBigBlind();
		setStartMoney();
		setAvatar();
		numberOfBots();
		SceneController.setScene(new MainMenu());
	}

	public void cancel() {
		SceneController.setScene(new MainMenu());
	}
	
	public void setName(){
		if(!name.getText().equals("") && !name.getText().equals("Set player Name") ){
			PokerApplication.name = name.getText();
		}
	}
	public void setStartMoney(){
		if(startValue.getText().matches("[0-9]+") && startValue.getText().length() > 1) {
			PokerApplication.bank = Integer.valueOf(startValue.getText());
			
		}
	}
	
	public void setSmallBlind(){
		if(small.getText().matches("[0-9]+") && small.getText().length() > 1) {
			PokerApplication.small = Integer.valueOf(small.getText());
			
		}
	}	
	public void setBigBlind(){
		if(big.getText().matches("[0-9]+") && big.getText().length() > 1) {
			PokerApplication.big = Integer.valueOf(big.getText());
			
		}	
	}	
	public void setAvatar(){
		if(avatar.getText().contains(".png")){
			System.out.println("Not yet implemented");
			//TODO
		}
				
	}
	public void numberOfBots(){
		if(numberBots.getText().matches("[0-9]+") && numberBots.getText().length() > 0 && Integer.valueOf(numberBots.getText()) < 10) {
			int numBots = Integer.valueOf(numberBots.getText());

			if (numBots < 3)
				numBots = 3;
			else if (numBots > 9)
				numBots = 9;

			PokerApplication.numberOfBots = numBots;
		}
				
	}
	
	public void setBotDificulty(){
		if(botButton.getText().equals("BOT IS EASY")){
			
			PokerApplication.diff =  GameBuilderAiDifficultyOptions.MIXED;
			botButton.setText("Bot is MIXED");
		}
		else if(botButton.getText().equals("Bot is MIXED")){
			PokerApplication.diff =  GameBuilderAiDifficultyOptions.ADVANCED;
			botButton.setText("Bot is HARD");
		}
		else if(botButton.getText().equals("Bot is HARD")){
			PokerApplication.diff =  GameBuilderAiDifficultyOptions.RANDOM;
			botButton.setText("BOT IS EASY");
		}	
	}
}
