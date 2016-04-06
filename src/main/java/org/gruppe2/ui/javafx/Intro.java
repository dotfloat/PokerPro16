package org.gruppe2.ui.javafx;

/*
 * Created by Petter on 04/04/2016.
 */

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.gruppe2.game.event.Event;

import javafx.event.ActionEvent;
import java.io.IOException;

public class Intro extends BorderPaneController {

    @FXML private ImageView logo;
    private DoubleProperty fitWidth;
    Stage prevStage;

    public Intro(){
        super();
        fitWidth = new SimpleDoubleProperty();
        logo.fitWidthProperty().bind(fitWidth.multiply(0.8));
    }

    public void goToMenu(ActionEvent event) throws IOException{
        System.out.println("Something happens.");
        Parent menuParent = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
        Scene menuScene = new Scene(menuParent);
        Stage menuStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        menuStage.setScene(menuScene);
        menuStage.show();

    }

    public DoubleProperty fitWidthProperty() {
        return fitWidth;
    }
    private void setPrevStage(Stage stage){
        prevStage = stage;
    }
}
