package org.gruppe2.ui.javafx.ingame;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
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
    @FXML
    private ChatBox chatBox;

    public GameScene() {
        UIResources.loadFXML(this);
        Game.setAnnotated(this);
        setKeyListener();
        chatBox.toFront();
    }

    private void setKeyListener() {
    	table.setOnKeyPressed(new EventHandler<KeyEvent>() {
    		
            @SuppressWarnings("incomplete-switch")
			@Override
            public void handle(KeyEvent event) {
            	System.out.println("key pressed");
                switch (event.getCode()) {
                    case UP:    choiceBar.onBetAction(); break;
                    case DOWN:  choiceBar.onFoldAction(); break;
                    case LEFT:  choiceBar.decreaseSlider(); break;
                    case RIGHT: choiceBar.increaseSlider(); break;    
                }
            }
        });
		
	}

	@Handler
    public void onQuit(QuitEvent event) {
        PlayerStatistics stats = statisticsModel.getPlayerStatistics().get(Game.getPlayerUUID());

        if (stats != null) {
            Main.savePlayerStatistics(stats);
        }
    }
}
