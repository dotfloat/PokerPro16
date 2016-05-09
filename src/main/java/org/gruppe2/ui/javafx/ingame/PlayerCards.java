package org.gruppe2.ui.javafx.ingame;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import org.gruppe2.game.Card;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.event.RoundStartEvent;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;
import org.gruppe2.ui.UIResources;

public class PlayerCards extends Pane {
   

    @Helper
    private RoundHelper roundHelper;

    @FXML
    private ImageView playerCard1;
    @FXML
    private ImageView playerCard2;

    public PlayerCards() {
        UIResources.loadFXML(this);
        Game.setAnnotated(this);
    }

    @Handler
    public void onRoundStart(RoundStartEvent event) {
        Optional<RoundPlayer> roundPlayer = roundHelper.findPlayerByUUID(Game.getPlayerUUID());

        setVisible(roundPlayer.isPresent());

        if (!roundPlayer.isPresent())
            return;

        Card[] cards = roundPlayer.get().getCards();

        playerCard1.setImage(UIResources.getCard(cards[0]));
        playerCard2.setImage(UIResources.getCard(cards[1]));
    }
}
