package org.gruppe2.ui.javafx;

/*
 * Created by Petter on 04/04/2016.
 */

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;

public class Intro extends BorderPaneController {

    @FXML private ImageView logo;
    private DoubleProperty fitWidth;

    public Intro(){
        super();
        fitWidth = new SimpleDoubleProperty();
//        logo.fitWidthProperty().bind(fitWidth.multiply(0.8));
    }

    public void goToMenu(ActionEvent event) throws IOException{
        System.out.println("Something happens.");
        SceneController.setScene((getClass().getResource("/views/MainMenu.fxml")));
        /*
        Parent root = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
        Stage menuStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene menuScene = new Scene(root);
        menuStage.setScene(menuScene);
        menuStage.show();
        */
        //BorderPane borderPane = PokerApplication.getRoot();
        //borderPane.setCenter(new BorderPane(FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"))));
    }

    public DoubleProperty fitWidthProperty() {
        return fitWidth;
    }
}
