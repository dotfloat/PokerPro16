package org.gruppe2.ui.javafx;

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
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Query;
import org.gruppe2.ui.Resources;

public class ChoiceBar extends HBox {
    static ObjectProperty<Font> fontTracking = new SimpleObjectProperty<>(Font.getDefault());

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
        setSizes();
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
	                    foldAction();
	                    break;
	                case RIGHT:
	                    betAction();
	                    break;
	                default:
	                    break;
	            }
	            actionQuery = null;
        	}
        });
    }

    @FXML
    private void foldAction() {
    	if(actionQuery != null){
    		
    		actionQuery = null;
    	}
    }

    @FXML
    private void betAction() {
    	if(actionQuery != null){
//        	InGame.getContext().message("chat", textField.getText());
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
        if (slider.getValue() == slider.getMax())
            btnBet.setText("ALL IN");
        else
            btnBet.setText("RAISE");
        return (int) slider.getValue() + " CHIPS";
    }

    /**
     * Removes eventpossibilities
     *
     * @param player
     */
    void updatePossibleBarsToClick() {
    	
        slider.setMax(1000);
        slider.setMin(0);
        slider.setValue(0);
    }
    
    @FXML
    public void onChatAction(ActionEvent event) {
    	InGame.getContext().message("chat", chatField.getText());
    }
    
    @Handler
    public void setActionHandler(PlayerActionQuery playerActionQuery){
    	if(playerActionQuery.getPlayer().getUUID().equals(InGame.getPlayerUUID()))
    		actionQuery = playerActionQuery.getPlayer().getAction();
    }
}
