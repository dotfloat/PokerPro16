package org.gruppe2.ui.javafx.ingame;

import java.util.UUID;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

import javafx.scene.layout.StackPane;
import org.gruppe2.game.Action;
import org.gruppe2.game.Player;
import org.gruppe2.game.PossibleActions;
import org.gruppe2.game.RoundPlayer;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Query;
import org.gruppe2.ui.UIResources;
import org.gruppe2.ui.javafx.PokerApplication;

import java.util.UUID;

/**
 * This is the bottom choicebar used to press an action (fold or bet)
 *
 * @author htj063
 */
public class ChoiceBar extends StackPane {
    @Helper
    private GameHelper gameHelper;
    @Helper
    private RoundHelper roundHelper;

    @FXML
    private HBox spectatorBar;
    @FXML
    private HBox playerBar;
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
        setEvents();
    }

    @FXML
    public void onJoinAction(ActionEvent event) {
        Game.getInstance().join();
    }

    @FXML
    public void setEvents() {
        slider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    sliderValue.textProperty().setValue(checkMaxBid(slider));
                });

    }

    @FXML
    public void onFoldAction() {
        if (actionQuery != null) {
            actionQuery.set(new Action.Fold());
            actionQuery = null;
        }
    }

    @FXML
    public void onBetAction() {
        if (actionQuery != null) {
            UUID uuid = Game.getPlayerUUID();
            Player player = gameHelper.findPlayerByUUID(uuid).get();
            RoundPlayer roundPlayer = roundHelper.findPlayerByUUID(uuid).get();
            PossibleActions possibleActions = roundHelper
                    .getPlayerOptions(uuid);

            int amount = (int) (slider.getValue() - (roundHelper
                    .getHighestBet() - roundPlayer.getBet()));

            if (amount == 0) {
                if (possibleActions.canCall()) {
                    actionQuery.set(new Action.Call());
                } else if (possibleActions.canCheck()) {
                    actionQuery.set(new Action.Check());
                } else {
                    throw new RuntimeException();
                }
            } else if (amount == player.getBank()) {
                actionQuery.set(new Action.AllIn());
            } else {
                actionQuery.set(new Action.Raise(amount));
            }

            actionQuery = null;
        }

    }

    public void increaseSlider() {
        slider.setValue(slider.getValue() * 2);
    }

    public void decreaseSlider() {
        slider.setValue(slider.getValue() / 2);
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
        else if (slider.getValue() > pa.getCallAmount())
            btnBet.setText("RAISE");
        else if (pa.canCall())
            btnBet.setText("Call");
        else
            btnBet.setText("Check");
        return (int) slider.getValue() + " CHIPS";
    }

    /**
     * Removes eventpossibilities
     */
    private void updatePossibleBarsToClick() {
        Player player = gameHelper.findPlayerByUUID(Game.getPlayerUUID()).get();
        slider.setMax(player.getBank());
        slider.setMin(0);
        slider.setValue(0);
    }

    @Handler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (event.getPlayer().getUUID().equals(Game.getPlayerUUID())) {
            spectatorBar.setVisible(false);
            playerBar.setVisible(true);
        }
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

            slider.setMin(actions.getCallAmount());
            slider.setMax(actions.getMaxRaise() + actions.getCallAmount());
            slider.setValue(actions.getCallAmount());

            actionQuery = query.getPlayer().getAction();
        }
    }
}