package org.gruppe2.frontend;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.gruppe2.backend.*;
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
    private ImageView profileImage = new ImageView(new Image (getClass().getResourceAsStream("/default.png")));

    public PlayerInfoBox(){
        super();
        getStyleClass().add("pane");
        add(name, 0, 0, 2, 1);
        add(profileImage, 0, 1, 1, 3);
        add(currentBet, 1, 2, 1, 1);
        add(chips, 1, 3, 1, 1);
        profileImage.setPreserveRatio(true);
        profileImage.setFitWidth(72);
    }

    public void updateInfoBox(Player player){
        if (player == null){
            setVisible(false);
            return;
        }
        name.setText(player.getName());
        chips.setText("CHIPS: " + player.getBank());
        currentBet.setText("BET: " + player.getBet());
    }
    public PlayerInfoBox(Player player){
        this();
        updateInfoBox(player);
    }

    public static List<PlayerInfoBox> createPlayerInfoBoxes(List<Player> players){
        List<PlayerInfoBox> playerInfoBoxes = new ArrayList<>();
        for (int i=0;i<9;i++){
            if (i >= players.size()){
                playerInfoBoxes.add(new PlayerInfoBox(null));
            }else playerInfoBoxes.add(new PlayerInfoBox(players.get(i)));
        }
        return playerInfoBoxes;
    }

    public boolean isFull(ArrayList<PlayerInfoBox> playerInfoBoxes) {
        for (PlayerInfoBox p : playerInfoBoxes) {
            if (p.getName() == null) return false;
        }
        return true;
    }

    public Label getName() {
        return name;
    }
    public void updateProfileImage(ImageView imageView){
    	profileImage = imageView;
    }
}
