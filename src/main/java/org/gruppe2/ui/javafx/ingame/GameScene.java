package org.gruppe2.ui.javafx.ingame;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import org.gruppe2.Main;
import org.gruppe2.ai.NewDumbAI;
import org.gruppe2.game.PlayerStatistics;
import org.gruppe2.game.event.PlayerWonEvent;
import org.gruppe2.game.event.QuitEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.model.StatisticsModel;
import org.gruppe2.game.session.*;
import org.gruppe2.ui.UIResources;
import org.gruppe2.ui.javafx.ConfettiOrMoney;
import org.gruppe2.ui.javafx.Modal;
import org.gruppe2.ui.javafx.SceneController;
import org.gruppe2.ui.javafx.ToolTip;

import java.util.*;

public class GameScene extends Pane {
    @Helper
    private GameHelper gameHelper;
    @Helper
    private RoundHelper roundHelper;
    @Model
    private StatisticsModel statisticsModel;

    @FXML
    private Table table;
    @FXML
    private ChoiceBar choiceBar;
    @FXML
    private ChatBox chatBox;

    public GameScene() {
        UIResources.loadFXML(this);
        Game.setAnnotated(this);

        chatBox.toFront();
    }

    @Handler
    public void onQuit(QuitEvent event) {
        PlayerStatistics stats = statisticsModel.getPlayerStatistics().get(Game.getPlayerUUID());

        if (stats != null) {
            Main.savePlayerStatistics(stats);
        }
    }

    @Handler
    public void onPlayerWon(PlayerWonEvent event){
        getChildren().add(new ConfettiOrMoney(100, true));
        String text = event.getPlayer().getName() + " won the game!";
        Label label = new Label(text);
        label.fontProperty().setValue(new Font(30));
        SceneController.setModal(label);
    }
}
