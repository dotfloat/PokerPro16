package org.gruppe2.ui.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.gruppe2.ui.Resources;

/**
 * Created by Petter on 11/04/2016.
 */
public class RightMenu extends VBox {

    @FXML VBox innerBox;

    public RightMenu(){
        Resources.loadFXML(this);
    }

    public void leaveTable(ActionEvent actionEvent) {

    }

    public void viewLobby(ActionEvent actionEvent) {

    }

    public void showMenu(ActionEvent actionEvent) {
        innerBox.setVisible(!innerBox.visibleProperty().get());
    }
}
