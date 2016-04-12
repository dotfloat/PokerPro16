package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import org.gruppe2.ui.Resources;

public class ChatBox extends TextArea {




    public ChatBox() {
        Resources.loadFXML(this);
        setPositionsAndSettings();
    }


    private void setPositionsAndSettings() {
        double chatScale = 0.22;
        this.prefWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(chatScale));
        this.prefHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(chatScale));
        this.maxWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(chatScale));
        this.maxHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(chatScale));

        editableProperty().setValue(false);
        setWrapText(true);

    }

    public void setEventListeners(TextField textField) {

        textField.setOnAction(e -> { // Put text from textField to textArea
            if (textField.getText().equals(null) || textField.getText().equals("")) setScrollTop(Double.MAX_VALUE);
            else {
                this.setText(this.getText() + "\n" + "General" + ": " + textField.getText());
                textField.setText("");
                this.setScrollTop(Double.MAX_VALUE);
            }
        });
    }
}
