package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
	@FXML private Button botButton;
	
	public Settings() {
		Resources.loadFXML(this);
		if(PokerApplication.diff == GameBuilderAiDifficultyOptions.RANDOM)
			botButton.setText("BOT IS EASY");
		else if(PokerApplication.diff == GameBuilderAiDifficultyOptions.MIXED)
			botButton.setText("Bot is MIXED");
		else
			botButton.setText("Bot is HARD");
	}

	public void ok() {
		SceneController.setScene(new MainMenu());
	}

	public void cancel() {
		SceneController.setScene(new MainMenu());
	}
	
	public void setName(){
		if(!name.getText().equals("")){
			PokerApplication.name = name.getText();
		}
	}
	public void setStartMoney(){
		if(startValue.getText().matches("[0-9]+") && startValue.getText().length() > 1) {
			PokerApplication.bank = Integer.valueOf(startValue.getText());
			
		}
	}
	
	public void setSmallBlind	(){
		if(small.getText().matches("[0-9]+") && small.getText().length() > 1) {
			PokerApplication.small = Integer.valueOf(small.getText());
			
		}
	}	
	public void setBigBlind	(){
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
