package org.gruppe2.ui.old_javafx;

import javafx.application.Platform;
import org.gruppe2.game.Action;
import org.gruppe2.game.Card;
import org.gruppe2.game.GameClient;
import org.gruppe2.game.Player;

import java.util.ArrayList;
import java.util.List;

public class GUIClient extends GameClient {
    private GUI gui;
    private volatile Action action = null;

    public GUIClient(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void onRoundStart() {
        Platform.runLater(() -> {
            gui.getMainFrame().paintPocketCards();
            System.out.println("roundStartTest");
        });
    }

    @Override
    public Action onTurn(Player player) {
        activateAndDeactivatePlayers(gui, player);
        gui.updateGUI(player);
        Action action = null;
        System.out.println("your turn player");
        while ((action = getAction()) == null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        setAction(null);
        System.out.println("Action: " + action);

        return action;
    }

    private void activateAndDeactivatePlayers(GUI gui, Player player) {
        for (PlayerInfoBox playerInfoBox : gui.playerInfoBoxes) {
            if (playerInfoBox.getPlayer() == player) {
                playerInfoBox.setActive();
            } else playerInfoBox.setInActive();
        }
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public void onCommunalCards(List<Card> communalCards) {
        ArrayList<Card> communityCards = (ArrayList<Card>) communalCards;
        Platform.runLater(() -> gui.getMainFrame().showCommunityCards(communityCards));
    }

    @Override

    public void onOtherPlayerTurn(Player player) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (gui.playerInfoBoxes == null) return;
                activateAndDeactivatePlayers(gui, player);
            }
        });
    }

    /**
     * Resets frame, this methods needs major cleanup in next iteration.
     */
    @Override
    public void onRoundEnd() {
        Platform.runLater(() -> {
            gui.getMainFrame().clearCommunityCards();

        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPlayerAction(Player player, Action action) {
        gui.updateGUI(player);
    }

    @Override
    public void onPlayerVictory(Player player) {
        gui.getMainFrame().playerWons(player);
        gui.getMainFrame().clearCommunityCards();
    }
}
