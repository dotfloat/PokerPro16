package org.gruppe2.ui.javafx;

import java.util.UUID;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import org.gruppe2.game.Player;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.event.PlayerPreActionEvent;
import org.gruppe2.game.event.RoundStartEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;
import org.gruppe2.ui.Resources;



public class ThisPlayerInfoBox extends HBox {
	private UUID playerUUID;
    RoundPlayer roundPlayer;
    Player player;
    
    @Helper
    private GameHelper gameHelper;
    @Helper
    private RoundHelper roundHelper;

    @FXML
    private Label playerName;
    @FXML
    private ImageView profileImage;
    @FXML
    private Label playerBet;
    @FXML
    private Label stack;

    public ThisPlayerInfoBox() {
        Resources.loadFXML(this);
        InGame.getContext().setAnnotated(this);
        playerUUID = InGame.getPlayerUUID();
        
        player = gameHelper.findPlayerByUUID(InGame.getPlayerUUID());
        bindToStage(playerName, profileImage, playerBet, stack);
        setSize();
    }

    private void setSize() {
        double boxScale = 0.35;
        this.paddingProperty().setValue(new Insets(10, 10, 10, 10));
        this.prefWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(boxScale));
        this.prefHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(boxScale * 0.2));
        this.maxWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(boxScale));
        this.maxHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(boxScale * 0.2));
        this.spacingProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.02));
    }

    private void bindToStage(Node... nodes) {
        for (Node n : nodes) {
            if (n instanceof Label) {
                ((Label) n).fontProperty().bind(ChoiceBar.fontTracking);
                ((Label) n).prefWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.1));
            } else if (n instanceof ImageView)
                ((ImageView) n).fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.05));
        }
    }

    public void setUp() {
        Player player = gameHelper.findPlayerByUUID(InGame.getPlayerUUID());
        RoundPlayer roundPlayer = roundHelper.findPlayerByUUID(InGame.getPlayerUUID());

        if (player == null) {
            setVisible(false);
            return;
        }
        playerName.setText(player.getName());
        stack.setText("$" + player.getBank());
        playerBet.setText("BET: " + roundPlayer.getBet());
    }

    public void update(Player currentPlayer) {

        if (player == null) {
            setVisible(false);
            return;
        }
        if(currentPlayer.getUUID().equals(playerUUID)){
	        playerName.setText(player.getName());
	        stack.setText("$" + player.getBank());
	        playerBet.setText("BET: " + roundPlayer.getBet());
	        updatePicture();
	        setActive();
        }
        else{
        	setInActive();
        }
    }

    public void updatePicture() {
        //TODO ----->
    }

    public void setActive() {
        getStyleClass().clear();
        getStyleClass().add("paneActive");
    }

    public void setInActive() {
        getStyleClass().clear();
        getStyleClass().add("pane");
    }
    
    @Handler
    public void roundStartBoxSetupHandler(RoundStartEvent roundStartEvent){
    	roundPlayer = roundHelper.findPlayerByUUID(playerUUID);
    }
    
    @Handler
    public void currentPlayerHandler(PlayerPreActionEvent playerPreActionEvent){
    	Player currentPlayer = playerPreActionEvent.getPlayer();
    	
    	update(currentPlayer);
    }

}
