package org.gruppe2.frontend;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.gruppe2.backend.*;
import org.gruppe2.backend.Player;

/*
*   An object to display player details when the game is being played.
*/

public class PlayerInfoBox {
    private Label name = new Label();
    private Label chips = new Label();
    private Label currentBet = new Label();

    public PlayerInfoBox(){
        VBox playerBox = new VBox(4);
        playerBox.getChildren().addAll(name, chips, currentBet);
        playerBox.getStyleClass().add("vbox");
    }
    public void updateInfoBox(Player player){
        name.setText("NAME: " + player.getName());
        chips.setText("CHIPS: " + player.getBank());
        currentBet.setText("BET: " + player.getBet());
    }

}
