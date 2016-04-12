package org.gruppe2.ui.javafx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import org.gruppe2.ui.Resources;

public class Statistics extends StackPane {
	String name = "src/main/resources/testLogs/2016.103_21.17.txt";
	
	@FXML private TextArea statistics;
	@FXML private TextField changeLog;
	
	public Statistics() {
		Resources.loadFXML(this);
		setStasticsTest(name);
		changeLog.setVisible(false);
	}

	private void setStasticsTest(String changedName) {
		name = changedName;
		String content = "";
		
		try {
			content = new String(Files.readAllBytes(Paths.get(name)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		statistics.setText(content);
		statistics.setWrapText(true);
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
	public void replay(){
		System.out.println("Replay STATS! PRONTO!");
	}
	public void changeLogFile(){
		if(!changeLog.getText().equals("") && !changeLog.getText().equals("Change Logg") ){
			name = changeLog.getText();
		}
	}
	
	
}
