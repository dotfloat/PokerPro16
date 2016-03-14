package org.gruppe2.frontend;

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

	public static void setStartValues(PokerGame pokerGame) {
		Group root = new Group();
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Edit Person");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		Scene scene = new Scene(root);
		dialogStage.setScene(scene);

		GridPane grid = new GridPane();
		createGrid(grid, root, dialogStage, pokerGame);
		
		
		dialogStage.showAndWait();

	}

	private static void createGrid(GridPane grid, Group root,
			Stage dialogStage, PokerGame pokerGame) {

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
				startMoneyField, pokerGame, dialogStage);

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
		
		setUpEmptyPot(pokerGame);
		root.getChildren().add(grid);
	}

	private static void setButtonAction(Button ok, Button cancel,
			TextField nameField, TextField smallBlindField,
			TextField bigBlindField, TextField startMoneyField,
			PokerGame pokerGame, Stage dialogStage) {

		ok.setOnAction(e -> {
			if (!nameField.getText().equals(null)) {
				if (moneyFieldsAreValid(startMoneyField, bigBlindField,
						smallBlindField)) {
					String name = nameField.getText();
					int startValue = Integer.valueOf(startMoneyField.getText());

					pokerGame.getPlayers().add(new Player(name, startValue,pokerGame.getTable()));

					int smallBlind = Integer.valueOf(smallBlindField.getText());
					int bigBlind = Integer.valueOf(smallBlindField.getText());

					pokerGame.smallBlind = smallBlind;
					pokerGame.bigBlind = bigBlind;

					dialogStage.close();
				}
			}
			else
				System.out.println("No name");
		});
		cancel.setOnAction(e -> {
			dialogStage.close();
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
	private static void setUpEmptyPot(PokerGame pokerGame){
		pokerGame.gui.getMainFrame().updateTablePot(pokerGame);
	}

}
