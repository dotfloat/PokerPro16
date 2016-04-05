package org.gruppe2.ui.javafx;

/*
 * Created by Petter on 04/04/2016.
 */

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class Intro extends BorderPaneController {

    @FXML private ImageView logo;
    private DoubleProperty fitWidth;

    public Intro(){
        super();
        fitWidth = new SimpleDoubleProperty();
        logo.fitWidthProperty().bind(fitWidth.multiply(0.8));
    }

    public void goToMenu(){
        System.out.println("Something happens.");
    }

    public double getFitWidth() {
        return fitWidth.get();
    }

    public DoubleProperty fitWidthProperty() {
        return fitWidth;
    }

    public void setFitWidth(double fitWidth) {
        this.fitWidth.set(fitWidth);
    }
}
