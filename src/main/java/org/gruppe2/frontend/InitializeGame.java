package org.gruppe2.frontend;

import org.gruppe2.backend.Player;

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


		GridPane grid = new GridPane();
		createGrid(grid, root, gui, primaryStage);


	}

	private static void createGrid(GridPane grid, Group root, GUI gui, Stage primaryStage) {

		Label nameLabel = new Label("Name:");
		TextField nameField = new TextField();

		Label smallBlindLabel = new Label("Small blind:");
		TextField smallBlindField = new TextField();

		Label bigBlindLabel = new Label("Big blind:");
		TextField bigBlindField = new TextField();

		Label startMoneyLabel = new Label("Start money:");
		TextField startMoneyField = new TextField();

		Button ok = new Button("ok");
		Button cancel = new Button("cancel");
		setButtonAction(ok, cancel, nameField, smallBlindField, bigBlindField,
				startMoneyField, gui, primaryStage);

		grid.add(nameLabel, 1, 1);
		grid.add(nameField, 2, 1);

		grid.add(smallBlindLabel, 1, 2);
		grid.add(smallBlindField, 2, 2);

		grid.add(bigBlindLabel, 1, 3);
		grid.add(bigBlindField, 2, 3);

		grid.add(startMoneyLabel, 1, 4);
		grid.add(startMoneyField, 2, 4);

		grid.add(ok, 1, 5);
		grid.add(cancel, 2, 5);
		
//		setUpEmptyPot(pokerGame);
		grid.setLayoutX(gui.getWidth()/2);
		grid.setLayoutY(gui.getHeight()/2 + gui.getHeight()*0.2);
		root.getChildren().add(grid);
	}

	private static void setButtonAction(Button ok, Button cancel,
			TextField nameField, TextField smallBlindField,
			TextField bigBlindField, TextField startMoneyField, GUI gui, Stage primaryStage) {

		ok.setOnAction(e -> {
			if (!nameField.getText().equals(null)) {
				if (moneyFieldsAreValid(startMoneyField, bigBlindField,
						smallBlindField)) {
					gui.startMainFrame(primaryStage,gui.root, gui.canvas);
					String name = nameField.getText();
					int startValue = Integer.valueOf(startMoneyField.getText());

//					pokerGame.getPlayers().add(new Player(name, startValue,pokerGame.getTable()));
					
					int smallBlind = Integer.valueOf(smallBlindField.getText());
					int bigBlind = Integer.valueOf(smallBlindField.getText());

//					pokerGame.smallBlind = smallBlind;
//					pokerGame.bigBlind = bigBlind;
					
					
				}
			}
			else
				System.out.println("No name");
		});
		cancel.setOnAction(e -> {
			
			System.out.println("cancel pressed");
			System.exit(0);
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
	/**
	 * Draws the pot in top middle of bord.
	 * @return
	 */
//	private static void setUpEmptyPot(){
//		gui.getMainFrame().updateTablePot(0);
//	}

}
