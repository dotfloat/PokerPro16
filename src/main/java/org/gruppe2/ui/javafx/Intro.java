package org.gruppe2.ui.javafx;

/*
 * Created by Petter on 04/04/2016.
 */

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class Intro extends BorderPaneController {

    @FXML private ImageView logo;
    private DoubleProperty fitWidth;

    public Intro(){
        super();
        //logo.fitWidthProperty().bind(PokerApplication.getRoot().widthProperty());
    }

    public void goToMenu() throws IOException{
        SceneController.setScene((getClass().getResource("/views/MainMenu.fxml")));
    }

    public DoubleProperty fitWidthProperty() {
        return fitWidth;
    }
}
