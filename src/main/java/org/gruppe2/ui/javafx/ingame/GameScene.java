package org.gruppe2.ui.javafx.ingame;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import org.gruppe2.Main;
import org.gruppe2.ai.NewDumbAI;
import org.gruppe2.game.PlayerStatistics;
import org.gruppe2.game.event.QuitEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.model.StatisticsModel;
import org.gruppe2.game.session.*;
import org.gruppe2.ui.UIResources;

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

    public GameScene() {
        boolean player = false;

        if (Main.getProperty("name").isEmpty()) {
            Main.setProperty("name", NewDumbAI.randomName());
        }

        if (Main.getProperty("avatar").isEmpty()) {
            String[] avatars = UIResources.listAvatars();
            Random random = new Random();

            Main.setProperty("avatar", avatars[random.nextInt(avatars.length)]);
        }

        if ((player = Game.message("addPlayer", Game.getPlayerUUID(), Main.getProperty("name"), Main.getProperty("avatar")).get())) {
            Game.message("addPlayerStatistics", Game.getPlayerUUID(), Main.loadPlayerStatistics());
        }

        UIResources.loadFXML(this);
        Game.setAnnotated(this);
    }

    @Handler
    public void onQuit(QuitEvent event) {
        PlayerStatistics stats = statisticsModel.getPlayerStatistics().get(Game.getPlayerUUID());

        if (stats != null) {
            Main.savePlayerStatistics(stats);
        }
    }
}
