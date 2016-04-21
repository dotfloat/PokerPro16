package org.gruppe2.ui.javafx;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.gruppe2.game.old.Card;
import org.gruppe2.ui.Resources;

/**
 * Created by Petter on 21/04/2016.
 */
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
        //pair
        innerBox.getChildren().add(new DifferentHandView("Pair",
                new Card(12, Card.Suit.CLUBS), new Card(12, Card.Suit.SPADES)));
        //two pairs
        innerBox.getChildren().add(new DifferentHandView("Two pairs",
                new Card(12, Card.Suit.CLUBS), new Card(12, Card.Suit.HEARTS), new Card(7, Card.Suit.SPADES), new Card(7, Card.Suit.DIAMONDS)));
        //three of a kind
        innerBox.getChildren().add(new DifferentHandView("Three of a kind",
                new Card(12, Card.Suit.CLUBS), new Card(12, Card.Suit.SPADES), new Card(12, Card.Suit.HEARTS)));
        //straight
        innerBox.getChildren().add(new DifferentHandView("Straight",
                new Card(5, Card.Suit.CLUBS), new Card(6, Card.Suit.SPADES), new Card(7, Card.Suit.HEARTS), new Card(8, Card.Suit.CLUBS), new Card(9, Card.Suit.SPADES)));
        //flush
        innerBox.getChildren().add(new DifferentHandView("Flush",
                new Card(6, Card.Suit.CLUBS), new Card(7, Card.Suit.CLUBS), new Card(10, Card.Suit.CLUBS), new Card(12, Card.Suit.CLUBS), new Card(13, Card.Suit.CLUBS)));
        //full house
        innerBox.getChildren().add(new DifferentHandView("Full house",
                new Card(12, Card.Suit.CLUBS), new Card(12, Card.Suit.SPADES), new Card(14, Card.Suit.SPADES), new Card(14, Card.Suit.DIAMONDS), new Card(14, Card.Suit.HEARTS)));
        //four of a kind
        innerBox.getChildren().add(new DifferentHandView("Four of a kind",
                new Card(11, Card.Suit.CLUBS), new Card(11, Card.Suit.SPADES), new Card(11, Card.Suit.HEARTS), new Card(11, Card.Suit.DIAMONDS)));
        //straight flush
        innerBox.getChildren().add(new DifferentHandView("Straight flush",
                new Card(7, Card.Suit.CLUBS), new Card(8, Card.Suit.CLUBS), new Card(9, Card.Suit.CLUBS), new Card(10, Card.Suit.CLUBS), new Card(11, Card.Suit.CLUBS)));
        //royal flush
        innerBox.getChildren().add(new DifferentHandView("Royal flush",
                new Card(10, Card.Suit.SPADES), new Card(11, Card.Suit.SPADES), new Card(12, Card.Suit.SPADES), new Card(13, Card.Suit.SPADES), new Card(14, Card.Suit.SPADES)));
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
