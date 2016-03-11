package org.gruppe2.frontend;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChoicePopup {
	
	
	public static void showChoices(PokerGame pokerGame, Player player) {
		Group root = new Group();
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Choose your destiny!");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		Scene scene = new Scene(root);
		dialogStage.setScene(scene);

		GridPane grid = new GridPane();
		createGrid(grid, root, dialogStage, pokerGame, player);
		
		
		// Show the dialog and wait until the user closes it
		dialogStage.showAndWait();
	}
	private static void createGrid(GridPane grid, Group root,
			Stage dialogStage, PokerGame pokerGame, Player player) {

		Button check = new Button("Check");
		Button call = new Button("Call");
		
		Label raise = new Label("Raise with:");
		TextField raiseField = new TextField();
		
		Button fold = new Button("Fold");

		Button okRaise = new Button("Ok raise");
		Button cancel = new Button("cancel");
		setButtonAction(okRaise, cancel, check, call, raiseField,
				fold, pokerGame, dialogStage, player);

		grid.add(check, 1, 1);
		grid.add(call, 1, 2);
		grid.add(raise, 1, 3);
		grid.add(raiseField, 2, 3);
		grid.add(fold, 1, 4);
		grid.add(okRaise, 1, 5);
		grid.add(cancel, 2, 5);
		
		root.getChildren().add(grid);
	}

	private static void setButtonAction(Button okRaise, Button cancel,
			Button check, Button call,
			TextField raiseField, Button fold,
			PokerGame pokerGame, Stage dialogStage, Player player) {

		check.setOnAction(e -> {
			player.doAction(Action.CHECK);
			dialogStage.close();
		});
		
		call.setOnAction(e -> {
			player.doAction(Action.CALL);
			dialogStage.close();
		});
		
		fold.setOnAction(e -> {
			player.doAction(Action.FOLD);
			dialogStage.close();
		});
		
		
		okRaise.setOnAction(e -> {
			if (!check.getText().equals(null)) {
					if(!raiseField.getText().equals(null)){
						player.doAction(Action.RAISE);
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

}
