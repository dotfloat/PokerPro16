package org.gruppe2.ui.javafx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import org.gruppe2.ui.Resources;

public class StatisticsMenu extends StackPane {
	private String name = "src/main/resources/testLogs/2016.104_09.52.txt";
	private String content;
	
	@FXML private TextArea statistics;
	@FXML private TextField changeLog;
	
	public StatisticsMenu() {
		Resources.loadFXML(this);
		setStasticsTest(name);
		changeLog.setVisible(false);
	}

	private void setStasticsTest(String changedName) {
		name = changedName;
		content = "";
		
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
		PokerApplication.replayMode = true;
		GameWindow gameWindow = new GameWindow();
		SceneController.setScene(gameWindow);
		
		Thread th = new Thread(() -> SetUpReplay(gameWindow));
		th.start();
		
		
	}
	
	public void changeLogFile(){
		if(!changeLog.getText().equals("") && !changeLog.getText().equals("Change Logg") ){
			name = changeLog.getText();
		}
	}
	private void SetUpReplay(GameWindow gameWindow) {	
		System.out.println(content);
		String[] lines = content.split("\n");
		System.out.println(lines.length);
		lineReader(lines,gameWindow);
	}

	private void lineReader(String[] lines, GameWindow gameWindow) {
		for(String line : lines){
			if(line.contains("Big Blind: ")){
//				gameWindow;
			}
		}
	}
	
}
