package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import org.gruppe2.game.old.Player;
import org.gruppe2.ui.Resources;

/**
 * Created by Petter on 21/04/2016.
 */
public class ThisPlayerInfoBox extends HBox {
    @FXML Label playerName;
    @FXML ImageView profileImage;
    @FXML Label playerBet;
    @FXML Label stack;
    Player player;

    public ThisPlayerInfoBox(){
        Resources.loadFXML(this);
        bindToStage(playerName, profileImage, playerBet, stack);
        setSize();

    }

    private void setSize(){
        double boxScale = 0.35;
        this.paddingProperty().setValue(new Insets(10,10,10,10));
        this.prefWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(boxScale));
        this.prefHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(boxScale*0.2));
        this.maxWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(boxScale));
        this.maxHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(boxScale*0.2));
        this.spacingProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.02));
    }

    private void bindToStage(Node... nodes){
        for(Node n : nodes) {
            if (n instanceof Label) {
                ((Label) n).fontProperty().bind(ChoiceBar.fontTracking);
                ((Label) n).prefWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.1));
            }
            else if (n instanceof ImageView) ((ImageView) n).fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.05));
        }
    }
    public void setUp(GameWindow gamewindow){

    	player = gamewindow.gameSession.getPlayers().get(0);

    	 if (player == null) {
             setVisible(false);
             return;
         }
         playerName.setText(player.getName());
         stack.setText("$" + player.getBank());
         playerBet.setText("BET: " + player.getBet());
    }

    public void update(){
    	 if (player == null) {
             setVisible(false);
             return;
         }
         playerName.setText(player.getName());
         stack.setText("$" + player.getBank());
         playerBet.setText("BET: " + player.getBet());
//         updatePicture();
    }

}
