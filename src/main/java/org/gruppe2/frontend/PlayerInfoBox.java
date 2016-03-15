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
        add(name, 0, 0, 2, 1);
        add(profileImage, 0, 1, 1, 2);
        add(currentBet, 1, 2, 1, 1);
        add(chips, 1, 3, 1, 1);
        setStyle("-fx-background-color: black");
        setMinWidth(400);
        setMaxHeight(400);
        setMinHeight(400);
        setMaxHeight(400);
    }

    public void updateInfoBox(Player player){
        name.setText("NAME: " + player.getName());
        chips.setText("CHIPS: " + player.getBank());
        currentBet.setText("BET: " + player.getBet());
    }
    public PlayerInfoBox(Player player){
        this();
        updateInfoBox(player);
    }

}
