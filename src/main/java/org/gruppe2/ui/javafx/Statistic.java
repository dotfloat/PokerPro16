package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.gruppe2.ui.Resources;

class Statistic extends BorderPane {

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
        initialize(ifMenu);
    }

    private void initialize(boolean ifMenu) {
        setWindowSize(ifMenu);
        setSizes();
        getStatistics();
    }

    private void getStatistics() {
        //TODO query statistics from backend
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
