package org.gruppe2.frontend;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsMenu extends Pane {
	MainMenu mainMenu;
	GUI gui;
	Group root;

	public SettingsMenu(MainMenu mainMenu, GUI gui, Group root) {
		this.mainMenu = mainMenu;
		this.gui = gui;
		this.root = root;
		// this.setMinHeight(gui.getHeight());
		// this.setMaxHeight(gui.getHeight());
		// this.setStyle("-fx-background-color: black");
	}

	public void showSettings() {
		Label resolutionText = new Label("RESOLUTION:");
		Button big = new Button("1920x1080");
		Button medium = new Button("1280x720");
		Button small = new Button("960x540");

		ChoiceBox<Button> resolutions = new ChoiceBox<Button>(
				FXCollections.observableArrayList(big, medium, small));

		resolutions.getSelectionModel();
		gui.canvas.setHeight(gui.getHeight());
		VBox vbox = new VBox();

		vbox.getChildren().addAll(resolutionText, resolutions);
		setEventListener(big, medium, small, vbox, resolutions);

		vbox.setLayoutX(gui.getWidth() / 2 - vbox.getWidth());
		vbox.setLayoutY(gui.getHeight() / 2 + gui.getHeight() * 0.2);
		vbox.setAlignment(Pos.CENTER);

		this.getChildren().add(vbox);
		root.getChildren().add(this);

	}

	private void setEventListener(Button big, Button medium, Button small,
			VBox vbox, ChoiceBox<Button> resolutions) {
		resolutions
				.getSelectionModel()
				.selectedItemProperty()
				.addListener(
						e -> {
							if (resolutions.getSelectionModel().isSelected(0)) {
								gui.setWindowSize(1920, 1080);

							} else if (resolutions.getSelectionModel()
									.isSelected(1)) {

								gui.setWindowSize(1280, 720);
							} else if (resolutions.getSelectionModel()
									.isSelected(2)) {
								gui.setWindowSize(960, 540);
							}
							this.getChildren().remove(vbox);
							mainMenu.getChildren().remove(0);
							root.getChildren().remove(mainMenu);
							
							mainMenu.setMainMenu((Stage) gui.scene.getWindow(),
									root, gui);
						});
	}
}
