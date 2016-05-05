package org.gruppe2.ui.javafx.ingame;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


import org.gruppe2.game.Action;
import org.gruppe2.game.Player;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.event.PlayerPostActionEvent;
import org.gruppe2.game.event.PlayerPreActionEvent;
import org.gruppe2.game.event.RoundStartEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;
import org.gruppe2.ui.UIResources;


public class ThisPlayerInfoBox extends HBox {
    @Helper
    private GameHelper game;
    @Helper
    private RoundHelper round;

    @FXML
    private Label name;
    @FXML
    private ImageView avatar;
	@FXML 
	private ImageView fold;
    @FXML
    private Label bank;
    @FXML
    private Label bet;
    @FXML
    private Label lastAction;

    public ThisPlayerInfoBox() {
        UIResources.loadFXML(this);
        Game.setAnnotated(this);
    }

    @Handler
    public void onRoundStart(RoundStartEvent e) {
        Optional<Player> player = game.findPlayerByUUID(Game.getPlayerUUID());
        Optional<RoundPlayer> roundPlayer = round.findPlayerByUUID(Game.getPlayerUUID());

        setVisible(player.isPresent());

        if (!player.isPresent())
            return;

        fold.setVisible(false);
        lastAction.setVisible(false);

        name.setText(player.get().getName());
        bank.setText(String.valueOf(player.get().getBank()));
        bet.setText("0");

        avatar.setImage(UIResources.getAvatar(player.get().getAvatar()));
    }

    @Handler
    public void onPreAction(PlayerPreActionEvent e){
        if (e.getPlayer().getUUID().equals(Game.getPlayerUUID())) {
            fold.setVisible(false);
            lastAction.setVisible(false);
            setActive(true);
        } else {
            setActive(false);
        }
    }

    @Handler
    public void onPostAction(PlayerPostActionEvent e){
        if (!e.getPlayer().getUUID().equals(Game.getPlayerUUID()))
            return;

        bank.setText(String.valueOf(e.getPlayer().getBank()));
        bet.setText(String.valueOf(e.getRoundPlayer().getBet()));

        if (e.getAction() instanceof Action.Fold) {
            fold.setVisible(true);
        } else {
            lastAction.setText(e.getAction().toString());
            lastAction.setVisible(true);
        }
    }

    private void setActive(boolean active) {
        getStyleClass().clear();
        getStyleClass().add(active ? "paneActive" : "pane");
    }
}
