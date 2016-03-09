package org.gruppe2.frontend;

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

public class InitializeGame {

	public static void setStartValues(TestSimulator simulator) {
		Group root = new Group();
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Edit Person");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		Scene scene = new Scene(root);
		dialogStage.setScene(scene);

		GridPane grid = new GridPane();
		createGrid(grid, root, dialogStage, simulator);
		// Set the person into the controller
		// PersonEditDialogController controller = loader.getController();
		// controller.setDialogStage(dialogStage);
		// controller.setPerson(person);

		// Show the dialog and wait until the user closes it
		dialogStage.showAndWait();
		System.out.println("hei");
		
		

	}

	private static void createGrid(GridPane grid, Group root, Stage dialogStage, TestSimulator simulator) {

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
				startMoneyField, simulator, dialogStage);

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
		root.getChildren().add(grid);
	}

	private static void setButtonAction(Button ok, Button cancel,
			TextField nameField, TextField smallBlindField,
			TextField bigBlindField, TextField startMoneyField, TestSimulator simulator, Stage dialogStage) {
		
		ok.setOnAction(e -> {
			if(!nameField.getText().equals(null)){
				if(!startMoneyField.getText().equals(null)){
					String name = nameField.getText();
					int startValue = Integer.valueOf(startMoneyField.getText());
					
					simulator.listOfPlayers.add(new Player(name, startValue));
					
					int smallBlind = Integer.valueOf(smallBlindField.getText());
					int bigBlind = Integer.valueOf(smallBlindField.getText());
					
					simulator.smallBlind = smallBlind;
					simulator.bigBlind = bigBlind;
					
					dialogStage.close();
				}
			}
		});
		cancel.setOnAction(e -> {
			dialogStage.close();
		});

	}

	public static void setPlayersToTable(TestSimulator simulator, GUI gui) {
		int playerNumber = 0;
		for(Player player : simulator.listOfPlayers){
			Label playerPosition = new Label(player.name+" "+player.currentChips);
			if(playerNumber == 0){
				playerPosition.setLayoutX(15);
				playerPosition.setLayoutY(300);
			}
			playerPosition.setTextFill(Color.HOTPINK);
			playerPosition.setFont(new Font(15));
			gui.getMainFrame().getChildren().add(playerPosition);
			playerNumber++;
		}
		
	}

}
