package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.gruppe2.game.Card;
import org.gruppe2.ui.Resources;

import java.util.List;

public class DifferentHandView extends VBox {
    @FXML private Label handName;
    @FXML private HBox cards;

    public DifferentHandView(String name, List<Card> elements){
        Resources.loadFXML(this);
        System.out.println(elements.size());
        handName.setText(name);
        handName.fontProperty().bind(ChoiceBar.fontTracking);
        for (int i=4;i>=0;i--){
            if (i < elements.size()) this.cards.getChildren().add(createCardImage(elements.get(i)));
            else {
                ImageView placeHolderCard = createCardImage(new Card(2, Card.Suit.CLUBS));
                placeHolderCard.setVisible(false);
                this.cards.getChildren().add(placeHolderCard);
            }
        }
    }

    public ImageView createCardImage(Card card) {
        String name = "/images/cards/" + getCardName(card) + ".png";

        Image image = new Image(getClass().getResourceAsStream(name), 200, 0, true, true);

        ImageView cardPic = new ImageView(image);
        cardPic.fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.025));
        cardPic.preserveRatioProperty().setValue(true);

        return cardPic;
    }

    public String getCardName(Card card) {
        String finalName = String.valueOf(card.getSuit().toString()
                .toLowerCase().charAt(0));

        if (card.getFaceValue() > 9)
            finalName += card.getFaceValue();
        else
            finalName += "0" + card.getFaceValue();

        return finalName;
    }
}
