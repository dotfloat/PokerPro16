package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import org.gruppe2.ui.Resources;

public class ChatBox extends ScrollPane {


    @FXML
    private TextArea textArea;


    public ChatBox() {
        Resources.loadFXML(this);
        setPositionsAndSettings();
    }


    private void setPositionsAndSettings() {
        double scale = 0.22;
        this.prefWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(scale));
        this.prefHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(scale));
        this.maxWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(scale));
        this.maxHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(scale));

        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        textArea.setEditable(false);

    }

    public void setEventListeners(TextField textField) {

        textField.setOnAction(e -> { // Put text from textField to textArea
            if (textField.getText().equals(null) || textField.getText().equals("")) ;
            else {
                textArea.setText(textArea.getText() + "\n" + "General" + ": " + textField.getText());
                textField.setText("");
            }
        });
    }
}
