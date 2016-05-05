package org.gruppe2.ui.javafx.ingame;


import java.util.UUID;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import javafx.scene.text.Font;
import org.gruppe2.game.Action;
import org.gruppe2.game.Player;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.event.PlayerPostActionEvent;
import org.gruppe2.game.event.PlayerPreActionEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;
import org.gruppe2.ui.Resources;
import org.gruppe2.ui.javafx.SceneController;
import org.gruppe2.ui.javafx.ToolTip;

public class PlayerInfoBox extends BorderPane {
    private UUID playerUUID = null;

    Player player;

    private ObjectProperty<Font> font = new SimpleObjectProperty<>();

    @Helper
    private RoundHelper roundHelper;
    @Helper
    private GameHelper gameHelper;

    @FXML
    private Label name;
    @FXML
    private Label bank;
    @FXML
    private Label bet;
    @FXML
    private ImageView avatar;
    @FXML
    private ImageView fold;
    @FXML
    private Label lastAction;

    PlayerInfoBox() {
        Resources.loadFXML(this);
        Game.setAnnotated(this);
    }

    public void setPlayerUUID(UUID uuid) {
        playerUUID = uuid;

        if (uuid == null) {
            setVisible(false);
            player = null;
            return;
        }

        setVisible(true);
        player = gameHelper.findPlayerByUUID(uuid);
        name.setText(player.getName());
        bank.setText(String.valueOf(player.getBank()));
        bet.setText("0");
        avatar.setImage(Resources.getAvatar(player.getAvatar()));
    }

    private void setActive() {
        getStyleClass().clear();
        getStyleClass().add("paneActive");
    }

    private void setInActive() {
        getStyleClass().clear();
        getStyleClass().add("pane");
    }


    @FXML
    public void viewStatistic(MouseEvent event) {
        SceneController.setTooltip(new ToolTip(new Statistic(false)), event.getSceneX(), event.getSceneY());
    }

    @Handler
    public void onPreAction(PlayerPreActionEvent event) {
        if (event.getPlayer().getUUID().equals(playerUUID)) {
            fold.setVisible(false);
            lastAction.setVisible(false);
            setActive();
        } else {
            setInActive();
        }
    }

    @Handler
    public void onPostAction(PlayerPostActionEvent event) {
        if (!event.getPlayer().getUUID().equals(playerUUID))
            return;

        bank.setText(String.valueOf(event.getPlayer().getBank()));
        bet.setText(String.valueOf(event.getRoundPlayer().getBet()));

        if (event.getAction() instanceof Action.Fold) {
            fold.setVisible(true);
        } else {
            lastAction.setText(event.getAction().getClass().getSimpleName());
            lastAction.setVisible(true);
        }
    }

    public boolean isPlayerActive() {
        for (RoundPlayer roundPlayer : roundHelper.getActivePlayers()) {
            if (roundPlayer.getUUID().equals(player.getUUID()))
                return true;
        }
        return false;
    }

    public Font getFont() {
        return font.get();
    }

    public ObjectProperty<Font> fontProperty() {
        return font;
    }

    public void setFont(Font font) {
        this.font.set(font);
    }
}
