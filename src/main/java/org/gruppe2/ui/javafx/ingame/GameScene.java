package org.gruppe2.ui.javafx.ingame;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.gruppe2.Main;
import org.gruppe2.ai.NewDumbAI;
import org.gruppe2.game.GameBuilder;
import org.gruppe2.game.Player;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.event.QuitEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.*;
import org.gruppe2.ui.Resources;
import org.gruppe2.ui.javafx.PokerApplication;

import java.util.*;
import java.util.TimerTask;

public class GameScene extends AnchorPane {
    @Helper
    private GameHelper gameHelper;
    @Helper
    private RoundHelper roundHelper;

    @FXML
    private Pot pot;
    @FXML
    private Table table;
    @FXML
    private ChoiceBar choiceBar;

    public GameScene() {
        boolean player = false;

        if ((player = Game.message("addPlayer", Game.getPlayerUUID(), "GUI", "default").get())) {
            Game.message("addPlayerStatistics", Game.getPlayerUUID(), Main.loadPlayerStatistics());
        }

        Resources.loadFXML(this);
        Game.setAnnotated(this);
    }
}
