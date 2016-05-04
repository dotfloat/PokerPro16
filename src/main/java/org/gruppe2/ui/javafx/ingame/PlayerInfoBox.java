package org.gruppe2.ui.javafx.ingame;


import java.util.UUID;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import org.gruppe2.game.Player;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.event.PlayerPostActionEvent;
import org.gruppe2.game.event.PlayerPreActionEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;
import org.gruppe2.ui.Resources;
import org.gruppe2.ui.javafx.PokerApplication;
import org.gruppe2.ui.javafx.SceneController;
import org.gruppe2.ui.javafx.ToolTip;

public class PlayerInfoBox extends BorderPane {
    private UUID playerUUID = null;
    Player player;
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
    @FXML
    private ImageView folded;

    PlayerInfoBox() {
        Resources.loadFXML(this);
        Game.setAnnotated(this);
        playerPicture.fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.06));
        folded.fitWidthProperty().bind(playerPicture.fitWidthProperty());
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
        if(player.getUUID().equals(playerUUID) && isPlayerActive()){
	        RoundPlayer roundPlayer = roundHelper.findPlayerByUUID(playerUUID);
	        playerName.setText(player.getName());
	        stack.setText("$" + player.getBank());
	        currentBet.setText("BET: " + roundPlayer.getBet());
	        updatePicture();
	        setActive();
	        player = null;
        }
        else
        	setInActive();
    }

    private void updatePicture() {
        if (roundHelper.findPlayerByUUID(playerUUID) == null)
            folded.visibleProperty().setValue(true);
        else
            folded.visibleProperty().setValue(false);

    }

    public void setActive() {
        getStyleClass().clear();
        getStyleClass().add("paneActive");
    }

    public void setInActive() {
        getStyleClass().clear();
        getStyleClass().add("pane");
    }

    @FXML
    public void viewStatistic(MouseEvent event) {
        SceneController.setTooltip(new ToolTip(new Statistic(false)), event.getSceneX(), event.getSceneY());
    }
    @Handler
    public void currentPlayerHandler(PlayerPreActionEvent playerPreActionEvent){
    	player = playerPreActionEvent.getPlayer();
    	if(player.getUUID().equals(playerUUID)){
    		setActive();
    	}
    	else{
    		setInActive();
    	}
    }
    @Handler
    public void currentPlayerHandler(PlayerPostActionEvent playerPreActionEvent){
    	player = playerPreActionEvent.getPlayer();
    	updateInfoBox();
    }
    public boolean isPlayerActive(){
    	for(RoundPlayer roundPlayer : roundHelper.getActivePlayers()){
    		if(roundPlayer.getUUID().equals(player.getUUID()))
    			return true;
    	}
    	return false;
    }
}
