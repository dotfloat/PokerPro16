package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import org.gruppe2.ui.Resources;

class Statistic extends StackPane {

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
        setWindowSize(ifMenu);
        setSizes();
    }

    private void setWindowSize(boolean ifMenu) {
        if (ifMenu) {
            this.maxWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.4));
            this.maxHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(0.4));
        } else {
            this.maxWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.2));
            this.maxHeightProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.1));
        }
    }

    @FXML
    public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) close();
    }

    @FXML
    public void close() {
        SceneController.removeStatistic(this);
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
