package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import org.gruppe2.ui.Resources;

/**
 * Created by Petter on 26/04/2016.
 */
public class ToolTip extends Pane {
    public ToolTip(Node node){
        Resources.loadFXML(this);
        getChildren().add(node);
    }
    @FXML
    public void close(MouseEvent event) {
        if (event.getTarget().equals(this)) System.out.println("hello");
        PokerApplication.getRoot().getChildren().remove(this);
    }
}
