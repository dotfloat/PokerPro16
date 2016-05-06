package org.gruppe2.ui.javafx.ingame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import org.gruppe2.game.Action;
import org.gruppe2.game.Player;
import org.gruppe2.game.PossibleActions;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Query;
import org.gruppe2.ui.UIResources;
import org.gruppe2.ui.javafx.PokerApplication;

import java.util.UUID;

public class ChoiceBar extends HBox {


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
        UIResources.loadFXML(this);
        Game.setAnnotated(this);
        setSizes();
        setEvents();
    }

    @FXML
    private void setSizes() {
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
            if (actionQuery != null) {
                switch (event.getCode()) {
                    case UP:
                        slider.increment();
                        break;
                    case DOWN:
                        slider.decrement();
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
        if (actionQuery != null) {
            actionQuery.set(new Action.Fold());
            actionQuery = null;
        }
    }

    @FXML
    private void onBetAction() {
        if (actionQuery != null) {
            UUID uuid = Game.getPlayerUUID();
            Player player = gameHelper.findPlayerByUUID(uuid).get();
            RoundPlayer roundPlayer = roundHelper.findPlayerByUUID(uuid).get();
            PossibleActions possibleActions = roundHelper.getPlayerOptions(uuid);

            int amount = (int) (slider.getValue() - (roundHelper.getHighestBet() - roundPlayer.getBet()));

            if (amount == 0) {
                if (possibleActions.canCall()) {
                    actionQuery.set(new Action.Call());
                } else if (possibleActions.canCheck()) {
                    actionQuery.set(new Action.Check());
                } else {
                    throw new RuntimeException();
                }
            } else if(amount == player.getBank()) {
                actionQuery.set(new Action.AllIn());
            } else {
                actionQuery.set(new Action.Raise(amount));
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
        PossibleActions pa = roundHelper.getPlayerOptions(Game.getPlayerUUID());

        if (slider.getValue() == slider.getMax())
            btnBet.setText("ALL IN");
        else if (slider.getValue() > 0)
            btnBet.setText("RAISE");
        else if (pa.canCall())
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
    private void updatePossibleBarsToClick() {
        Player player = gameHelper.findPlayerByUUID(Game.getPlayerUUID()).get();
        slider.setMax(player.getBank());
        slider.setMin(0);
        slider.setValue(0);
    }

    @FXML
    public void onChatAction(ActionEvent event) {
        Game.message("chat", chatField.getText(), Game.getPlayerUUID());
        chatField.setText("");
    }

    @Handler
    public void onActionQuery(PlayerActionQuery query) {
        if (!query.getPlayer().getUUID().equals(Game.getPlayerUUID())) {
            btnFold.setDisable(true);
            btnBet.setDisable(true);
            slider.setDisable(true);
            sliderValue.setDisable(true);
        } else {
            PossibleActions actions = roundHelper.getPlayerOptions(Game.getPlayerUUID());

            btnFold.setDisable(false);
            btnBet.setDisable(false);
            slider.setDisable(false);
            sliderValue.setDisable(false);

            int diffBet = roundHelper.getHighestBet() - query.getRoundPlayer().getBet();

            slider.setMin(diffBet);
            slider.setMax(actions.getMaxRaise() + diffBet);

            actionQuery = query.getPlayer().getAction();
        }
    }
}
