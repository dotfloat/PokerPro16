package org.gruppe2.ui.javafx;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import org.gruppe2.Main;
import org.gruppe2.game.GameBuilder;
import org.gruppe2.game.Player;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.event.QuitEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.ClientSession;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Session;
import org.gruppe2.game.session.SessionContext;
import org.gruppe2.network.NetworkClient;
import org.gruppe2.network.NetworkServerGameSession;
import org.gruppe2.ui.Resources;

public class InGame extends BorderPane {
    private static SessionContext context = null;
    private static UUID playerUUID = UUID.randomUUID();
    private List<Pane> playerInfoBoxes = new ArrayList<>();
    public static boolean UUIDSet = false;
    private static Timer sessionTimer = new Timer();

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

    InGame() {
        
        contextSetup();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Resources.loadFXML(this);
        setUpViewItems();
    }

    private void contextSetup() {
    	if(!PokerApplication.networkStart){
	    	context = new GameBuilder().start();
	        context.waitReady();
	        context.message("listen");
    	}
    	else{
    		context = Session.start(ClientSession.class, "localhost");
            context.waitReady();
    	}
        context.setAnnotated(this);
        context.message("addPlayer", playerUUID, "TestPlayer", "default");
        context.message("addPlayerStatistics", playerUUID, Main.loadPlayerStatistics());
    }

    private void setUpViewItems() {

        sessionTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> InGame.getContext().getEventQueue().process());
            }
        }, 0, 50);
        List<Pane> playerInfoBoxes = new ArrayList<Pane>();
        paintAllPlayers(playerInfoBoxes);
        onelinePressStart();


    }

    private void onelinePressStart() {
        if (NetworkClient.onlineGame) {
            Label pressStart = new Label(" Click to Start game");
            pressStart.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) { //Change to message when ready
                    NetworkClient.clientPressedStart = true;
                    NetworkServerGameSession.playerHasStartedGame = true;
                    System.out.println("pressed start");
                }
            });

            SceneController.setModal((new Modal(pressStart)));
        }
    }

    public static UUID getPlayerUUID() {
        return playerUUID;
    }

    public static void setPlayerUUID(UUID uuid) {
        playerUUID = uuid;
        UUIDSet = true;
    }

    public static SessionContext getContext() {
        return context;
    }

    public static void setContext(SessionContext context2) {
        context = context2;
    }

    @Handler
    public void setUpPlayer(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.getUUID().equals(playerUUID)) {
            PlayerInfoBox playerInfoBox = new PlayerInfoBox();
            playerInfoBoxes.add(playerInfoBox);
            playerInfoBox.setValues(player.getUUID());
        }

        paintAllPlayers(playerInfoBoxes);
    }

    private void paintAllPlayers(List<Pane> playerInfoBoxes) {

        int numberOfPlayers = playerInfoBoxes.size();
        if (numberOfPlayers > 4)
            paintPlayerInfoBox(playerInfoBoxes.get(4), 0.3, 0.001);
        if (numberOfPlayers > 8)
            paintPlayerInfoBox(playerInfoBoxes.get(8), 0.45, 0.001);
        if (numberOfPlayers > 5)
            paintPlayerInfoBox(playerInfoBoxes.get(5), 0.6, 0.002);
        if (numberOfPlayers > 2)
            paintPlayerInfoBox(playerInfoBoxes.get(2), 0.19, 0.001);
        if (numberOfPlayers > 3)
            paintPlayerInfoBox(playerInfoBoxes.get(3), 0.69, 0.001);
        if (numberOfPlayers > 6)
            paintPlayerInfoBox(playerInfoBoxes.get(6), 0.05, 0.2);
        if (numberOfPlayers > 7)
            paintPlayerInfoBox(playerInfoBoxes.get(7), 0.8, 0.2);
        if (numberOfPlayers > 0)
            paintPlayerInfoBox(playerInfoBoxes.get(0), 0.03, 0.45);
        if (numberOfPlayers > 1)
            paintPlayerInfoBox(playerInfoBoxes.get(1), 0.82, 0.45);
    }

    private void paintPlayerInfoBox(Pane playerInfoBox, double x, double y) {

        playerInfoBox.maxWidthProperty().bind(
                PokerApplication.getRoot().widthProperty().multiply(0.05));
        playerInfoBox.maxHeightProperty().bind(
                PokerApplication.getRoot().heightProperty().multiply(0.05));

        playerInfoBox.layoutXProperty().bind(
                PokerApplication.getRoot().widthProperty().multiply(x));
        playerInfoBox.layoutYProperty().bind(
                PokerApplication.getRoot().heightProperty().multiply(y));
        if (getChildren().contains(playerInfoBox))
            getChildren().remove(playerInfoBox);
        getChildren().add(playerInfoBox);
    }

    public void updateGameWindow(Player player) {
        Platform.runLater(() -> {
            for (Pane playerInfoBox : playerInfoBoxes) {
                ((PlayerInfoBox) playerInfoBox).updateInfoBox();
            }
            choiceBar.updatePossibleBarsToClick();
            pot.updatePot(roundHelper.getModel().getPot());
        });
    }

    public static void quit() {
        if (context != null) {
            context.quit();
            sessionTimer.cancel();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            context.getEventQueue().process();
            context = null;
            sessionTimer = null;
        }
    }
    PathTransition pathTransition = new PathTransition();

    @Handler
    public void ohNo(QuitEvent e) {
        System.out.println("Oh no, we are quiittingg");
    }
}
