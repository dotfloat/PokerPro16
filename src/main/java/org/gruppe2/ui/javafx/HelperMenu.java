package org.gruppe2.ui.javafx;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.gruppe2.ui.Resources;

/**
 * Created by Petter on 21/04/2016.
 */
public class HelperMenu extends VBox {
    @FXML VBox innerBox;

    public HelperMenu(){
        Resources.loadFXML(this);
        setSizes();
        innerBox.setLayoutX(-innerBox.getWidth());
    }

    private void setSizes() {
        innerBox.prefWidthProperty().bind(
                PokerApplication.getRoot().widthProperty().multiply(0.15));
        innerBox.maxWidthProperty().bind(
                PokerApplication.getRoot().widthProperty().multiply(0.15));
        innerBox.setVisible(false);

        this.maxWidthProperty().bind(
                PokerApplication.getRoot().widthProperty().multiply(0.2));
        this.maxHeightProperty().bind(
                PokerApplication.getRoot().heightProperty().multiply(0.2));
    }
    public void showMenu() {
        menuAnimation();
    }

    private void menuAnimation() {
        if (!innerBox.isVisible()) {
            innerBox.setVisible(true);
            innerBox.setTranslateX(-innerBox.getWidth());
        }
        TranslateTransition openMenu = new TranslateTransition(new Duration(300), innerBox);
        openMenu.setToX(0);
        TranslateTransition closeMenu = new TranslateTransition(new Duration(300), innerBox);
        if (innerBox.getTranslateX() != 0) {
            openMenu.play();
        } else {
            closeMenu.setToX(-innerBox.getWidth());
            closeMenu.play();
        }
    }
}
