package org.gruppe2.frontend;

import javafx.scene.layout.VBox;
import org.gruppe2.ai.AIClient;
import org.gruppe2.backend.Player;

import com.sun.security.ntlm.Client;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * All GUI related methods for initializing the chosen game
 * @author htj063
 *
 */
public class InitializeGame {

	public static void setStartValues(GUI gui, Group root, Stage primaryStage) {


		VBox vBox = new VBox(10);
		createGrid(vBox, root, gui, primaryStage);


	}

	private static void createGrid(VBox vBox, Group root, GUI gui, Stage primaryStage) {

		double buttonWidth = gui.getWidth()*0.4;
		double buttonHeight = gui.getHeight()*0.05;

		TextField nameField = new TextField();
		nameField.setPromptText("ENTER YOUR NAME");
		nameField.setPrefWidth(buttonWidth);
		nameField.setPrefHeight(buttonHeight);

		TextField smallBlindField = new TextField();
		smallBlindField.setPromptText("SET SMALL BLIND");
		smallBlindField.setPrefWidth(buttonWidth);
		smallBlindField.setPrefHeight(buttonHeight);

		TextField bigBlindField = new TextField();
		bigBlindField.setPromptText("SET BIG BLIND");
		bigBlindField.setPrefWidth(buttonWidth);
		bigBlindField.setPrefHeight(buttonHeight);

		TextField startMoneyField = new TextField();
		startMoneyField.setPromptText("SET AMOUNT OF CHIPS");
		startMoneyField.setPrefWidth(buttonWidth);
		startMoneyField.setPrefHeight(buttonHeight);


		Button ok = new Button("OK");
		ok.setPrefWidth(buttonWidth);
		ok.setPrefHeight(buttonHeight);

		Button cancel = new Button("CANCEL");
		cancel.setPrefWidth(buttonWidth);
		cancel.setPrefHeight(buttonHeight);

		setButtonAction(ok, cancel, nameField, smallBlindField, bigBlindField, startMoneyField, gui, primaryStage, vBox);

		vBox.setLayoutX(gui.getWidth()*0.5 - buttonWidth*0.5);
		vBox.setLayoutY(gui.getHeight()*0.5 + buttonHeight);
		vBox.getChildren().addAll(nameField, smallBlindField, bigBlindField, startMoneyField, cancel, ok);
		
//		setUpEmptyPot(pokerGame);
		root.getChildren().add(vBox);
	}

	private static void setButtonAction(Button ok, Button cancel,
										TextField nameField, TextField smallBlindField,
										TextField bigBlindField, TextField startMoneyField, GUI gui, Stage primaryStage, VBox vBox) {

		ok.setOnAction(e -> {
			if (!nameField.getText().equals(null)) {
				if (moneyFieldsAreValid(startMoneyField, bigBlindField,
						smallBlindField)) {
					gui.root.getChildren().remove(vBox);
					gui.startMainFrame(primaryStage,gui.root, gui.canvas);
					String name = nameField.getText();
					int startValue = Integer.valueOf(startMoneyField.getText());
					
					int smallBlind = Integer.valueOf(smallBlindField.getText());
					int bigBlind = Integer.valueOf(smallBlindField.getText());
					
					gui.getClient().getSession().addPlayer(name, gui.getClient());
					
//					gui.getClient().getSession().getPlayers().get(0).setBank(startValue);
//					gui.getClient().getSession().	
				}
			}
		});
		cancel.setOnAction(e -> {
			gui.root.getChildren().remove(vBox);
			gui.newMainMenu((Stage) gui.scene.getWindow(), gui.root);
		});

	}
	
	

	private static boolean moneyFieldsAreValid(TextField startMoneyField,
			TextField bigBlindField, TextField smallBlindField) {
		
		boolean nonIsNull = !startMoneyField.getText().equals(null)
				&& !smallBlindField.getText().equals(null)
				&& !bigBlindField.getText().equals(null);
		
		if(nonIsNull){
			boolean allAreInt =  startMoneyField.getText().matches("\\d+") && smallBlindField.getText().matches("\\d+") && bigBlindField.getText().matches("\\d+");
			return allAreInt;
		}

		return false;
	}
	
}
