package org.gruppe2.game.view;

import org.gruppe2.game.controller.GameController;
import org.gruppe2.game.event.EventHandler;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.session.SessionContext;
import org.gruppe2.game.controller.AbstractPlayerController;

import java.util.Map;
import java.util.UUID;

public class GameView extends AbstractView<GameModel, GameController> {

    public GameView(GameController controller) {
        super(controller);
    }

    public void onPlayerJoin(EventHandler<PlayerJoinEvent> handler) {
        getController().getSessionContext().getEventQueue().registerHandler(PlayerJoinEvent.class, handler);
    }

    public PlayerView getPlayerByName(String name) {
        for (AbstractPlayerController player : getController().getPlayers().values()) {
            if (player.getModel().getName().equals(name)) {
                return player.getView();
            }
        }

        return null;
    }
}
