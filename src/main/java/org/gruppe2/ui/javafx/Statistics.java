package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

import org.gruppe2.ui.Resources;

public class Statistics extends StackPane {
	
	@FXML private TextArea statistics;
	
	public Statistics() {
		Resources.loadFXML(this);
	}

	public void ok() {
		SceneController.setScene(new MainMenu());
	}

	public void cancel() {
		SceneController.setScene(new MainMenu());
	}
	
	public void getStatistics(){
		System.out.println("WHere are those GOD DAMN STATS! PRONTO!");
	}	
}
