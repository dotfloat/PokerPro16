package org.gruppe2.ui.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;


/**
 *
 */
public class MainMenu implements Initializable{
	@FXML
    private  Button  CreateTable;
	@FXML
    private  Button  JoinTable;
	@FXML
    private  Button SinglePlayer;
	@FXML
    private  Button  ViewStatistics;
	@FXML
    private  Button  Settings;
     
     
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		SinglePlayer.setOnAction(e -> SceneController.setScene( getClass().getResource("/views/GameWindow.fxml")));
		
	}

}
