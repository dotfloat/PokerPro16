package org.gruppe2.ui.old_javafx;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * All GUI related methods for initializing the chosen game
 *
 * @author htj063
 */
public class InitializeGame {

    public static void setStartValues(GUI gui, Group root, Stage primaryStage) {


        VBox vBox = new VBox(10);
        createGrid(vBox, root, gui, primaryStage);


    }

    private static void createGrid(VBox vBox, Group root, GUI gui, Stage primaryStage) {

        double buttonWidth = gui.getWidth() * 0.4;
        double buttonHeight = gui.getHeight() * 0.05;

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

        vBox.setLayoutX(gui.getWidth() * 0.5 - buttonWidth * 0.5);
        vBox.setLayoutY(gui.getHeight() * 0.5 + buttonHeight);
        vBox.setMaxHeight(gui.getHeight() * 0.4);
        vBox.getChildren().addAll(nameField, smallBlindField, bigBlindField, startMoneyField, ok, cancel);

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

                    String name = nameField.getText();
                    int startValue = Integer.valueOf(startMoneyField.getText());

                    int smallBlind = Integer.valueOf(smallBlindField.getText());
                    int bigBlind = Integer.valueOf(smallBlindField.getText());
                    gui.meName = name;
                    gui.getClient().setName(name);
                    gui.startValue = startValue;
                    gui.smallBlind = smallBlind;
                    gui.bigBlind = bigBlind;

                    gui.startMainFrame(primaryStage, gui.root, gui.canvas); //THIS IS START GAME!!!!

                }
            }
        });
        cancel.setOnAction(e -> {
            gui.root.getChildren().remove(vBox);
            gui.newMainMenu((Stage) gui.scene.getWindow(), gui.root);
        });

    }


    /**
     * Checks if money inputs in main menu screen are valid.
     *
     * @param startMoneyField
     * @param bigBlindField
     * @param smallBlindField
     * @return
     */
    private static boolean moneyFieldsAreValid(TextField startMoneyField,
                                               TextField bigBlindField, TextField smallBlindField) {

        boolean nonIsNull = !startMoneyField.getText().equals(null)
                && !smallBlindField.getText().equals(null)
                && !bigBlindField.getText().equals(null);

        if (nonIsNull) {
            boolean allAreInt = startMoneyField.getText().matches("\\d+") && smallBlindField.getText().matches("\\d+") && bigBlindField.getText().matches("\\d+");
            return allAreInt;
        }

        return false;
    }

}
