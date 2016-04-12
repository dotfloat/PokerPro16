package org.gruppe2.ui.javafx;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

import org.gruppe2.ui.Resources;

public class Statistics extends StackPane {
	
	@FXML private TextArea statistics;
	
	public Statistics() {
		Resources.loadFXML(this);
		setStasticsTest();
	}

	private void setStasticsTest() {
		String name = "/testLogs/2016.103_21.17.txt";
		System.out.println(name);
		String everything = "";
		try(BufferedReader br = new BufferedReader(new FileReader(name))) {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    everything = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		statistics.setText(everything);
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
	
}
