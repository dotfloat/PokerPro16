package org.gruppe2.ui.old_javafx;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Displays all possible settings
 *
 * @author htj063
 */
public class SettingsMenu extends Pane {
    MainMenu mainMenu;
    GUI gui;
    Group root;

    public SettingsMenu(MainMenu mainMenu, GUI gui, Group root) {
        this.mainMenu = mainMenu;
        this.gui = gui;
        this.root = root;
    }

    public void showSettings() {
        Label resolutionText = new Label("RESOLUTION:");
        Button big = new Button("1920x1080");
        Button medium = new Button("1280x720");
        Button small = new Button("960x540");
        Button cancel = new Button("Cancel");
        ChoiceBox<String> resolutions = new ChoiceBox<String>(
                FXCollections.observableArrayList(big.getText(), medium.getText(), small.getText()));

        resolutions.getSelectionModel();
        gui.canvas.setHeight(gui.getHeight());
        VBox vbox = new VBox();
        vbox.setSpacing(gui.getHeight() / 20);

        vbox.getChildren().addAll(resolutionText, resolutions, cancel);
        setEventListener(big, medium, small, vbox, resolutions, cancel);

        vbox.setLayoutX(gui.getWidth() / 2 - vbox.getWidth());
        vbox.setLayoutY(gui.getHeight() / 2 + gui.getHeight() * 0.2);
        vbox.setAlignment(Pos.CENTER);

        this.getChildren().add(vbox);
        root.getChildren().add(this);

    }

    /**
     * Create event listener for buttons pushed in settings choice box
     *
     * @param big
     * @param medium
     * @param small
     * @param vbox
     * @param resolutions
     * @param cancel
     */
    private void setEventListener(Button big, Button medium, Button small,
                                  VBox vbox, ChoiceBox<String> resolutions, Button cancel) {
        resolutions
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        e -> { // Set new resolution and Go back to main menu
                            if (resolutions.getSelectionModel().isSelected(0)) {
                                gui.setWindowSize(1920, 1080);

                            } else if (resolutions.getSelectionModel()
                                    .isSelected(1)) {

                                gui.setWindowSize(1280, 720);
                            } else if (resolutions.getSelectionModel()
                                    .isSelected(2)) {
                                gui.setWindowSize(960, 540);
                            }
                            resetFrameToMainMenu(vbox);
                        });

        cancel.setOnAction(e -> { // Go back to main menu
            resetFrameToMainMenu(vbox);
        });

    }

    /**
     * Goes back to main menu
     *
     * @param vbox
     */
    public void resetFrameToMainMenu(VBox vbox) {
        this.getChildren().remove(vbox);
        mainMenu.getChildren().remove(0);
        root.getChildren().remove(mainMenu);
        mainMenu.setMainMenu((Stage) gui.scene.getWindow(),
                root, gui);
    }
}