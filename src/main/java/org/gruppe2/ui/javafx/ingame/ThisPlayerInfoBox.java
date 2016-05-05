package org.gruppe2.ui.javafx.ingame;

import java.util.Optional;
import java.util.UUID;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


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
import org.gruppe2.ui.javafx.PokerApplication;



public class ThisPlayerInfoBox extends HBox {
	private UUID playerUUID;
    RoundPlayer roundPlayer;
    Optional<Player> player;

    @Helper
    private GameHelper gameHelper;
    @Helper
    private RoundHelper roundHelper;

    @FXML
    private Label playerName;
    @FXML
    private ImageView profileImage;
	@FXML 
	private ImageView fold;
    @FXML
    private Label playerBet;
    @FXML
    private Label stack;

    public ThisPlayerInfoBox() {
        UIResources.loadFXML(this);
        Game.setAnnotated(this);
        playerUUID = Game.getPlayerUUID();
        player = gameHelper.findPlayerByUUID(playerUUID);
        bindToStage(playerName, profileImage, playerBet, stack);
        setSize();
        System.out.println(profileImage);
//        profileImage.setImage(UIResources.getAvatar(player.get().getAvatar()));
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
                ((Label) n).prefWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.1));
            } else if (n instanceof ImageView)
                ((ImageView) n).fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.05));
        }
    }

    public void setUp() {
        Player player = gameHelper.findPlayerByUUID(Game.getPlayerUUID()).get();
        RoundPlayer roundPlayer = roundHelper.findPlayerByUUID(Game.getPlayerUUID()).get();

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
	        playerName.setText(player.get().getName());
	        stack.setText("$" + player.get().getBank());
	        if(roundPlayer != null){
	        	playerBet.setText("BET: " + roundPlayer.getBet());
	        }
	        updatePicture();
	        setActive();
        }
        else{
        	setInActive();
        }
    }

    public void updatePicture() {
    	if(roundPlayer != null){
    		if(roundHelper.getActivePlayers().contains(roundPlayer))
    			fold.setVisible(true);
    		else
    			fold.setVisible(false);
    	}
    	
    		
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
    public void onRoundStart(RoundStartEvent roundStartEvent){
    	Optional<RoundPlayer> p = roundHelper.findPlayerByUUID(playerUUID);
    	
    	if (p.isPresent()) {
    		roundPlayer = p.get();
    	}
    	
    	setVisible(p.isPresent());
    }
    
    @Handler
    public void currentPlayerHandler(PlayerPreActionEvent playerPreActionEvent){
    	Player currentPlayer = playerPreActionEvent.getPlayer();
    	if(currentPlayer.getUUID().equals(playerUUID)){
    		setActive();
    	}
    }
    @Handler
    public void currentPlayerHandler(PlayerPostActionEvent playerPreActionEvent){
    	Player currentPlayer = playerPreActionEvent.getPlayer();
    	update(currentPlayer);
    }

}
