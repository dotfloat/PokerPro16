package org.gruppe2.ui.javafx;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.gruppe2.game.Player;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Helper;
import org.gruppe2.ui.Resources;

import java.util.UUID;

public class PlayerInfoBox extends BorderPane {
    private UUID playerUUID = null;

    @Helper
    private RoundHelper roundHelper;
    @Helper
    private GameHelper gameHelper;

    @FXML
    private Label playerName;
    @FXML
    private Label stack;
    @FXML
    private Label currentBet;
    @FXML
    private ImageView playerPicture;

    PlayerInfoBox() {
        Resources.loadFXML(this);
        InGame.getContext().setAnnotated(this);
        playerPicture.fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.06));
        playerName.fontProperty().bind(ChoiceBar.fontTracking);
        stack.fontProperty().bind(ChoiceBar.fontTracking);
        currentBet.fontProperty().bind(ChoiceBar.fontTracking);

    }

    void setValues(UUID playerUUID) {
        this.playerUUID = playerUUID;

        playerName.setText(gameHelper.findPlayerByUUID(playerUUID).getName());
        currentBet.setText("0");
        stack.setText(String.valueOf(gameHelper.findPlayerByUUID(playerUUID).getBank()));
    }

    void updateInfoBox() {
        if (playerUUID == null) {
            setVisible(false);
            return;
        }

        Player player = gameHelper.findPlayerByUUID(playerUUID);
        RoundPlayer roundPlayer = roundHelper.findPlayerByUUID(playerUUID);

        playerName.setText(player.getName());
        stack.setText("$" + player.getBank());
        currentBet.setText("BET: " + roundPlayer.getBet());
        updatePicture();
    }

    private void updatePicture() {
        if (roundHelper.findPlayerByUUID(playerUUID) == null)
            playerPicture.setImage(new Image
                    (getClass().getResourceAsStream("/images/avatars/defaultFolded.png")));
        else
            playerPicture.setImage(new Image
                    (getClass().getResourceAsStream("/images/avatars/default.png")));

    }

    public void setActive() {
        getStyleClass().clear();
        getStyleClass().add("paneActive");
    }

    public void setInActive() {
        getStyleClass().clear();
        getStyleClass().add("pane");
    }

    public void viewStatistic() {
        SceneController.setModal(this);
    }
}
