package org.gruppe2.ui.javafx.ingame;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import org.gruppe2.game.Action;
import org.gruppe2.game.Player;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.event.*;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;
import org.gruppe2.ui.UIResources;


public class ThisPlayerInfoBox extends HBox {
    
	CountDownBar countDownBar = new CountDownBar();
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
    Player player;
    private boolean isActive;

    public ThisPlayerInfoBox() {
        UIResources.loadFXML(this);
        Game.setAnnotated(this);
        countDownBar.setAlignment(Pos.BOTTOM_LEFT);
        getChildren().add(countDownBar);
    }

    @Handler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().getUUID().equals(Game.getPlayerUUID()))
            return;

        Optional<Player> player = game.findPlayerByUUID(Game.getPlayerUUID());

        setVisible(player.isPresent());

        if (!player.isPresent())
            return;
        else this.player = player.get();
        fold.setVisible(false);
        lastAction.setVisible(false);

        name.setText(player.get().getName());
        bank.setText(String.valueOf(player.get().getBank()));
        bet.setText("0");

        avatar.setImage(UIResources.getAvatar(player.get().getAvatar()));
    }

    @Handler
    public void onPlayerLeave(PlayerLeaveEvent e) {
        if (!e.getPlayer().getUUID().equals(Game.getPlayerUUID()))
            return;

        setVisible(false);
    }

    @Handler
    public void onRoundStart(RoundStartEvent e) {
        Optional<RoundPlayer> roundPlayer = round.findPlayerByUUID(Game.getPlayerUUID());
        fold.setVisible(false);
    }

    @Handler
    public void onPreAction(PlayerPreActionEvent e){
        if (e.getPlayer().getUUID().equals(Game.getPlayerUUID())) {
        	countDownBar.startProgressBarTimer();
            fold.setVisible(false);
            lastAction.setVisible(false);
            isActive = true;
            setActive(isActive);
        } else {
            isActive = false;
            setActive(isActive);
        }
    }

    @Handler
    public void onPostAction(PlayerPostActionEvent e){
        if (!e.getPlayer().getUUID().equals(Game.getPlayerUUID()))
            return;
        countDownBar.stopProgressBar();
        bank.setText(String.valueOf(e.getPlayer().getBank()));
        bet.setText(String.valueOf(e.getRoundPlayer().getBet()));

        if (e.getAction() instanceof Action.Fold) {
            fold.setVisible(true);
        } else {
            lastAction.setText(e.getAction().toString());
            lastAction.setVisible(true);
        }
    }

    @FXML
    public void hover(){
        if (player == null)
            return;

        Color color = UIResources.getAvatarColor(player.getAvatar());
        setBackground(new Background(new BackgroundFill(color.darker(), new CornerRadii(5), null)));

    }

    @FXML
    public void noHover(){
        setActive(isActive);
    }

    private void setActive(boolean active) {
        getStyleClass().clear();
        getStyleClass().add(active ? "paneActive" : "pane");
    }
}
