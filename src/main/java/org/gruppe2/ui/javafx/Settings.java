package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import org.gruppe2.ui.Resources;

public class Settings extends StackPane {
	
	@FXML private TextField name;
	@FXML private TextField  small;	
	@FXML private TextField  big; 
	@FXML private TextField  startValue;
	@FXML private TextField  avatar;
	
	public Settings() {
		Resources.loadFXML(this);
	}

	public void ok() {
		SceneController.setScene(new MainMenu());
	}

	public void cancel() {
		SceneController.setScene(new MainMenu());
	}
	
	public void setName(){
		if(!startValue.getText().equals("")){
			System.out.println("Not yet implemented");
			//TODO
		}
	}
	public void setStartMoney(){
		if(startValue.getText().matches("[0-9]+") && startValue.getText().length() > 1) {
			System.out.println("Not yet implemented");
			//TODO
		}
	}
	
	public void setSmallBlind	(){
		if(startValue.getText().matches("[0-9]+") && startValue.getText().length() > 1) {
			System.out.println("Not yet implemented");
			//TODO
		}
	}	
	public void setBigBlind	(){
		if(startValue.getText().matches("[0-9]+") && startValue.getText().length() > 1) {
			System.out.println("Not yet implemented");
			//TODO
		}	
	}	
	public void setAvatar(){
		if(startValue.getText().contains(".png")){
			System.out.println("Not yet implemented");
			//TODO
		}
				
	}	
	
	
}
