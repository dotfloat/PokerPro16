package org.gruppe2.ui.javafx.ingame;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import org.gruppe2.game.Card;
import org.gruppe2.game.event.CommunityCardsEvent;
import org.gruppe2.game.event.RoundEndEvent;
import org.gruppe2.game.event.RoundStartEvent;
import org.gruppe2.game.session.Handler;
import org.gruppe2.ui.UIResources;
import org.gruppe2.ui.javafx.PokerApplication;

public class CommunityCards extends HBox {
    private List<ImageView> imageViews = new ArrayList<>();

    public CommunityCards() {
        super(5);
        Game.setAnnotated(this);

        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(UIResources.getCardBack());

            imageView.setPreserveRatio(true);
            imageView.fitWidthProperty().bind(maxWidthProperty().divide(5));
            imageView.fitHeightProperty().bind(maxHeightProperty());
            imageView.setSmooth(true);

            imageViews.add(imageView);
            getChildren().add(imageView);
        }

        setVisible(false);
    }

    @Handler
    public void onRoundStart(RoundStartEvent event) {
        resetImages();
        setVisible(true);
    }

    @Handler
    public void onCommunityCards(CommunityCardsEvent event) {
        resetImages();
        setVisible(true);

        for (int i = 0; i < event.getCards().size(); i++) {
            imageViews.get(i).setImage(UIResources.getCard(event.getCards().get(i)));
        }
    }

    @Handler
    public void onRoundEnd(RoundEndEvent event) {
        setVisible(false);
    }

    private void resetImages() {
        imageViews.forEach(view -> view.setImage(UIResources.getCardBack()));
    }
}
