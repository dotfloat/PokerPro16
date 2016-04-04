package org.gruppe2.frontend;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.gruppe2.backend.Player;

import java.util.ArrayList;
import java.util.List;

/*
 *   An object to display player details when the game is being played.
 */

public class PlayerInfoBox extends GridPane {

    private Label name = new Label();
    private Label chips = new Label();
    private Label currentBet = new Label();
    private ImageView profileImage = new ImageView(new Image(getClass().getResourceAsStream("/default.png")));
    private Player player;

    public PlayerInfoBox() {
        super();
        getStyleClass().add("pane");
        add(name, 0, 0, 2, 1);
        add(profileImage, 0, 1, 1, 3);
        add(currentBet, 1, 2, 1, 1);
        add(chips, 1, 3, 1, 1);
        profileImage.setPreserveRatio(true);
        profileImage.setFitWidth(72);
    }

    public PlayerInfoBox(Player player) {
        this();
        updateInfoBox(player);
    }

    public static List<PlayerInfoBox> createPlayerInfoBoxes(List<Player> players) {
        List<PlayerInfoBox> playerInfoBoxes = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (i >= players.size()) {
                playerInfoBoxes.add(new PlayerInfoBox(null));
            } else playerInfoBoxes.add(new PlayerInfoBox(players.get(i)));
        }
        return playerInfoBoxes;
    }

    public void updateInfoBox(Player player) {
        if (player == null) {
            setVisible(false);
            return;
        }
        this.player = player;
        name.setText(player.getName());
        chips.setText("CHIPS: " + player.getBank());
        currentBet.setText("BET: " + player.getBet());
        updatePicture();

    }

    public Label getName() {
        return name;
    }

    public void setActive() {
        getStyleClass().clear();
        getStyleClass().add("paneActive");
    }

    public void setInActive() {
        getStyleClass().clear();
        getStyleClass().add("pane");
    }

    public Player getPlayer() {
        return player;
    }

    private void updatePicture() {
        if (player.getClient().getSession().playerHasFolded(player))
            profileImage.setImage(new Image
                    (getClass().getResourceAsStream("/defaultFolded.png")));
        else
            profileImage.setImage(new Image
                    (getClass().getResourceAsStream("/default.png")));

    }
}
