package org.gruppe2.ui.javafx.ingame;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import org.gruppe2.game.Action;
import org.gruppe2.game.Player;
import org.gruppe2.game.PossibleActions;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Query;
import org.gruppe2.ui.Resources;
import org.gruppe2.ui.javafx.PokerApplication;

public class ChoiceBar extends HBox {
    static ObjectProperty<Font> fontTracking = new SimpleObjectProperty<>(Font.getDefault());
    
    
    @Helper
    private GameHelper gameHelper;
    @Helper
    private RoundHelper roundHelper;
    
    @FXML
    private TextField chatField;
    @FXML
    private Button btnFold;
    @FXML
    private Slider slider;
    @FXML
    private Label sliderValue;
    @FXML
    private Button btnBet;
    
    private Query<Action> actionQuery = null;

    public ChoiceBar() {
        Resources.loadFXML(this);
        InGame.getContext().setAnnotated(this);
        setSizes();
        setEvents();
    }

    @FXML
    private void setSizes() {
        setFontListener();

        this.spacingProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.02));

        slider.prefWidthProperty().bind(
                PokerApplication.getRoot().widthProperty().multiply(0.2));
        slider.minWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.05));
        chatField.minWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.21));
        chatField.prefWidthProperty().bind(
                PokerApplication.getRoot().widthProperty().multiply(0.22));

        sliderValue.prefWidthProperty().bind(
                PokerApplication.getRoot().widthProperty().multiply(0.09));
        chatField.prefWidthProperty().bind(
                PokerApplication.getRoot().widthProperty().multiply(0.10));
        btnFold.prefWidthProperty().bind(
                PokerApplication.getRoot().widthProperty().multiply(0.09));
        btnBet.prefWidthProperty().bind(
                PokerApplication.getRoot().widthProperty().multiply(0.09));

    }

    private void setFontListener() {
        btnBet.fontProperty().bind(fontTracking);
        btnFold.fontProperty().bind(fontTracking);
        chatField.fontProperty().bind(fontTracking);
        this.widthProperty().addListener((observableValue, oldWidth, newWidth) -> {
            fontTracking.set(Font.font(newWidth.doubleValue() / 70));
        });
    }

    @FXML
    public void setEvents() {
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            sliderValue.textProperty().setValue(checkMaxBid(slider));
        });
        setKeyListener();
    }

    /**
     * Makes it possible to use keys to play, instead of mouse
     */
    private void setKeyListener() {
        chatField.setOnKeyPressed(event -> {
        	if(actionQuery != null){
	            switch (event.getCode()) {
	                case UP:
	                    slider.setValue(slider.getValue() * 2);
	                    break;
	                case DOWN:
	                    slider.setValue(slider.getValue() / 2);
	                    break;
	                case LEFT:
	                	onFoldAction();
	                    break;
	                case RIGHT:
	                	onBetAction();
	                    break;
	                default:
	                    break;
	            }
	            
        	}
        });
    }

    @FXML
    private void onFoldAction() {
    	if(actionQuery != null){
    		actionQuery.set( new Action.Fold());
    		actionQuery = null;
    	}
    }

    @FXML
    private void onBetAction() {
    	System.out.println("start");
    	if(actionQuery != null){
    		PossibleActions pa = roundHelper.getPlayerOptions(InGame.getPlayerUUID());
    		System.out.println("inside");
    		if(pa.canCheck()){
    			actionQuery.set( new Action.Check());
    			System.out.println("Check");
    		}
    		else if(pa.canCall() && slider.getValue() == 0 ){
    			actionQuery.set( new Action.Call());
    			System.out.println("Call");
    		}
    		else if(pa.canRaise()){
    			if(slider.getValue() > 0){
    				System.out.println("Raise");
    				actionQuery.set( new Action.Raise( (int)slider.getValue()));
    			}
    		}
    		actionQuery = null;
    	}
    }

    /**
     * If you raise all you have, change text of raise button to ALL IN
     *
     * @param slider
     * @return
     */
    private String checkMaxBid(Slider slider) {
    	PossibleActions pa = roundHelper.getPlayerOptions(InGame.getPlayerUUID());
        if (slider.getValue() == slider.getMax())
            btnBet.setText("ALL IN");
        else if(slider.getValue() > 0)
            btnBet.setText("RAISE");
        else if(pa.canCall())
        	 btnBet.setText("Call");
        else
        	 btnBet.setText("Check");
        return (int) slider.getValue() + " CHIPS";
    }

    /**
     * Removes eventpossibilities
     *
     * @param player
     */
    void updatePossibleBarsToClick() {
    	Player player = gameHelper.findPlayerByUUID(InGame.getPlayerUUID());
        slider.setMax(player.getBank());
        slider.setMin(0);
        slider.setValue(0);
    }
    
    @FXML
    public void onChatAction(ActionEvent event) {
    	InGame.getContext().message("chat", chatField.getText(), InGame.getPlayerUUID());
    	chatField.setText("");
    }
    
    @Handler
    public void setActionHandler(PlayerActionQuery playerActionQuery){
    	if(playerActionQuery.getPlayer().getUUID().equals(InGame.getPlayerUUID())){
    		actionQuery = playerActionQuery.getPlayer().getAction();
    		updatePossibleBarsToClick();
    	}
    }
    public static ObjectProperty<Font> getFontTracking(){
    	return fontTracking;
    }
}
