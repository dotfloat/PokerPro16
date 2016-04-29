package org.gruppe2.ui.javafx.ingame;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import org.gruppe2.game.Player;
import org.gruppe2.game.PlayerStatistics;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.model.StatisticsModel;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Model;
import org.gruppe2.ui.Resources;
import org.gruppe2.ui.javafx.PokerApplication;

class Statistic extends BorderPane {

    @Model
    private StatisticsModel model;
    @Helper
    private GameHelper gameHelper;

    @FXML
    private Label name;
    @FXML
    private Label gamesPlayed;
    @FXML
    private Label gamesWon;
    @FXML
    private Label folded;
    @FXML
    private Label call;
    @FXML
    private Label check;
    @FXML
    private Label totalBet;
    @FXML
    private Label averageBet;
    @FXML
    private Label balance;


    public Statistic(boolean ifMenu) {
        Resources.loadFXML(this);
        InGame.getContext().setAnnotated(this);
        initialize(ifMenu);
    }

    private void initialize(boolean ifMenu) {
        setWindowSize(ifMenu);
        setSizes();
        getStatistics();
    }

    private void getStatistics() {
        PlayerStatistics stats = model.getPlayerStatistics().get(InGame.getPlayerUUID());
        Player player = gameHelper.findPlayerByUUID(InGame.getPlayerUUID());

        name.setText(player.getName());
        gamesPlayed.setText(String.valueOf(stats.getGamesPlayed()));
        gamesWon.setText(String.valueOf(stats.getGamesWon()));
        folded.setText(String.valueOf(stats.getTimesFolded()));
        call.setText(String.valueOf(stats.getTimesCalled()));
        check.setText(String.valueOf(stats.getTimesChecked()));
    }

    private void setWindowSize(boolean ifMenu) {
        if (ifMenu) {
            this.maxWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.3));
            this.maxHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(0.2));
        } else {
            this.maxWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.2));
            this.maxHeightProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.1));
        }
    }

    private void setSizes() {
        name.fontProperty().bind(ChoiceBar.fontTracking);
        gamesPlayed.fontProperty().bind(ChoiceBar.fontTracking);
        gamesWon.fontProperty().bind(ChoiceBar.fontTracking);
        folded.fontProperty().bind(ChoiceBar.fontTracking);
        call.fontProperty().bind(ChoiceBar.fontTracking);
        check.fontProperty().bind(ChoiceBar.fontTracking);
        totalBet.fontProperty().bind(ChoiceBar.fontTracking);
        averageBet.fontProperty().bind(ChoiceBar.fontTracking);
        balance.fontProperty().bind(ChoiceBar.fontTracking);
    }
}