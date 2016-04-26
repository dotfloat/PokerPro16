package org.gruppe2.ui.javafx;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.gruppe2.ui.Resources;

/**
 * Created by Petter on 26/04/2016.
 */
public class Modal extends Pane {

    public Modal(){
        Resources.loadFXML(this);
    }

    public void addChildren(Node... nodes){
        this.getChildren().addAll(nodes);
    }
}
