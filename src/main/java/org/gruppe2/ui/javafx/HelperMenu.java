package org.gruppe2.ui.javafx;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.gruppe2.game.Card;
import org.gruppe2.game.Cards;
import org.gruppe2.ui.Resources;

public class HelperMenu extends VBox {
    @FXML
    VBox innerBox;
    @FXML
    ScrollPane entirePane;
    @FXML
    Label title;

    public HelperMenu() {
        Resources.loadFXML(this);
        setSizes();
        innerBox.setLayoutX(-innerBox.getWidth());
        addAllHands();
    }

    //Create all hands possible
    private void addAllHands() {

    }

    private void setSizes() {

        title.fontProperty().bind(ChoiceBar.fontTracking);
        entirePane.prefWidthProperty().bind(this.prefWidthProperty().multiply(0.9));
        entirePane.maxWidthProperty().bind(this.maxWidthProperty().multiply(0.9));
        entirePane.setVisible(false);
        entirePane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        innerBox.maxWidthProperty().bind(entirePane.maxWidthProperty());

        this.maxWidthProperty().bind(
                PokerApplication.getRoot().widthProperty().multiply(0.22));
        this.maxHeightProperty().bind(
                PokerApplication.getRoot().heightProperty().multiply(0.6));
    }

    public void showMenu() {
        menuAnimation();
    }

    private void menuAnimation() {
        if (!entirePane.isVisible()) {
            entirePane.setVisible(true);
            entirePane.setTranslateX(-entirePane.getWidth());
        }
        TranslateTransition openMenu = new TranslateTransition(new Duration(300), entirePane);
        openMenu.setToX(0);
        TranslateTransition closeMenu = new TranslateTransition(new Duration(300), entirePane);
        if (entirePane.getTranslateX() != 0) {
            openMenu.play();
        } else {
            closeMenu.setToX(-entirePane.getWidth());
            closeMenu.play();
        }
    }
}
