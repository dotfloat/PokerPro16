package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.gruppe2.game.Card;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Helper;
import org.gruppe2.ui.Resources;

public class PlayerCards extends HBox {
    private boolean firstSet = true;

    @Helper
    private RoundHelper roundHelper;

    @FXML
    private ImageView playerCard1;
    @FXML
    private ImageView playerCard2;

    public PlayerCards() {
        Resources.loadFXML(this);
        InGame.getContext().setAnnotated(this);
    }

    /**
     * This is just test method for proof of concept, change this when backend
     * is ready with playerCards.
     */
    public void setPlayerCards(CommunityCards communityCardsBox) {
        Card[] cards = roundHelper.findPlayerByUUID(InGame.getPlayerUUID()).getCards();
        setPaneStyle();

        playerCard1.setImage(new Image(("/images/cards/"
                + communityCardsBox.getCardName(cards[0]) + ".png")));
        playerCard2.setImage(new Image(("/images/cards/"
                + communityCardsBox.getCardName(cards[1]) + ".png")));

        checkFirstSet();
    }

    private void setPaneStyle() {
        this.layoutXProperty().bind(
                PokerApplication.getRoot().widthProperty().multiply(0.82));
        this.layoutYProperty().bind(
                PokerApplication.getRoot().heightProperty().multiply(0.77));

    }

    /**
     * First time needs to set layout
     */
    private void checkFirstSet() {
        if (firstSet) {
            playerCard1.fitWidthProperty().bind(
                    PokerApplication.getRoot().widthProperty().multiply(0.10));
            playerCard1.setPreserveRatio(true);
            playerCard1.setSmooth(true);


            playerCard2.fitWidthProperty().bind(
                    PokerApplication.getRoot().widthProperty().multiply(0.10));
            playerCard2.setPreserveRatio(true);
            playerCard2.setSmooth(true);


            playerCard1.setRotate(350);
            playerCard2.setRotate(10);

            firstSet = false;
        }
    }
}
