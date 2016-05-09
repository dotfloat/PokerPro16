package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import org.gruppe2.ui.UIResources;

/**
 * Created by Petter on 26/04/2016.
 */

public class Modal extends StackPane {
    StackPane stage = PokerApplication.getRoot();

    public Modal(Node node) {
        UIResources.loadFXML(this);
        addChildren(node);
    }

    public void addChildren(Node... nodes) {
        this.getChildren().addAll(nodes);
    }

    @FXML
    public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) stage.getChildren().remove(this);
    }

    @FXML
    public void close(MouseEvent event) {
        if (event.getTarget().equals(this)) stage.getChildren().remove(this);
    }

}
