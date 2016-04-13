package org.gruppe2.ui.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import org.gruppe2.ui.Resources;

/**
 * Created by Petter on 13/04/2016.
 */
public class Statistic extends BorderPane {

    @FXML Label name;
    @FXML Label gamesPlayed;
    @FXML Label gamesWon;
    @FXML Label folded;
    @FXML Label call;
    @FXML Label check;
    @FXML Label totalBet;
    @FXML Label averageBet;
    @FXML Label balance;

    public Statistic(boolean ifMenu){
        Resources.loadFXML(this);
        setWindowSize(ifMenu);
        setSizes();
    }

    private void setWindowSize(boolean ifMenu) {
        if (ifMenu) {
            this.maxWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.6));
            this.maxHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(0.6));
        }
        else {
            this.maxWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.3));
            this.maxHeightProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.3));
        }
    }

    public void keyPressed(KeyEvent event){
        System.out.println("hello");
        if (event.getCode() == KeyCode.ESCAPE) SceneController.removeModal(this);
    }

    public void close(){
        SceneController.removeModal(this);
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
