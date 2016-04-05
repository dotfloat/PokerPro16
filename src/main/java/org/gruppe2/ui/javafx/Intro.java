package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * Created by Petter on 04/04/2016.
 */
public class Intro extends GridPaneController {

    @FXML Label holaSenor;
    @FXML ImageView logo;

    public void goToMenu(){
        System.out.println("Something happens.");
    }

}
