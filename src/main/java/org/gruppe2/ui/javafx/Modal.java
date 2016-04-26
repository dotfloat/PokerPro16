package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.gruppe2.ui.Resources;

/**
 * Created by Petter on 26/04/2016.
 */

public class Modal extends StackPane {

    public Modal(Node node){
        Resources.loadFXML(this);
        addChildren(node);
    }

    public void addChildren(Node... nodes){
        this.getChildren().addAll(nodes);
    }

    @FXML
    public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) close();
    }

    @FXML
    public void close() {
        SceneController.removeStatistic(this);
    }
}
