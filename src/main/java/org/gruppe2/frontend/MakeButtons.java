package org.gruppe2.frontend;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class MakeButtons {
	Button restart, pause, rotate, exit;
	GridPane grid = new GridPane();

	public void makeButton(BorderPane border, GUI gui, Group root) {
		
		//Make menu
		new TopMenu(grid, gui);
		
		// Buttons
		restart = new Button("Restart");
		restart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				System.out.println("Restart");
				gui.restart();
				gui.setPaused(false);
				gui.setStep(0);
			}
		});

		pause = new Button("Pause");
		pause.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				if (!gui.isPaused()) {
					pause.setText("fortsett");
					gui.getTimer().stop();
					gui.setPaused(true);
				} else {
					pause.setText("Pause");
					gui.getTimer().start();
					gui.setPaused(false);
				}
			}
		});

		

		exit = new Button("Exit");
		exit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				System.out.println("Exit");
				Platform.exit();
			}
		});
		// creating object buttons
		GridPane grid1 = new GridPane();
		
		// Add to parrent scene
		grid.add(restart, 1, 1);
		grid.add(pause, 2, 1);
		grid.add(exit, 4, 1);
		border.setTop(grid);
		border.setLeft(grid1);
	}

	
}
