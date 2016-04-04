package org.gruppe2.frontend;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.gruppe2.backend.Player;

public class ChatBox extends Pane {

    private Player player;

    public ChatBox(GUI gui, Player player) {
        super();
        this.player = player;
        createChatBox(gui);


    }

    private void createChatBox(GUI gui) {
        ScrollPane scroll = new ScrollPane();
        TextArea textArea = new TextArea();
        TextField textField = new TextField();

        scroll.setContent(textArea);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        textArea.setEditable(false);


        setEventListeners(textField, scroll, textArea);

//		vbox.setMaxWidth(gui.getWidth()/7);
//		vbox.setMaxHeight(gui.getHeight()/10);

        setScalesAndPositions(textField, gui, scroll);

    }


    private void setEventListeners(TextField textField, ScrollPane scroll, TextArea textArea) {

        textField.setOnAction(e -> { // Put text from textField to textArea
            if (textField.getText().equals(null) || textField.getText().equals("")) ;
            else {
                textArea.setText(textArea.getText() + "\n" + player.getName() + ": " + textField.getText());
                textField.setText("");
            }
        });

        scroll.heightProperty().addListener((observable, oldVal, newVal) -> {
            scroll.setVvalue(((Double) newVal).doubleValue());
        });
    }

    private void setScalesAndPositions(TextField textField, GUI gui, ScrollPane scroll) {
        textField.setLayoutY(textField.getLayoutY() + gui.getHeight() * 0.01);


        scroll.setLayoutX(gui.getWidth() * 0.01);
        scroll.setLayoutY(gui.getHeight() * 0.8);

        scroll.setMaxWidth(gui.getWidth() / 6);
        scroll.setMaxHeight(gui.getHeight() / 9);

        this.setMaxWidth(gui.getWidth() / 0.7);

        gui.getMainFrame().getChildren().add(scroll);
        getChildren().add(textField);
    }


}
