package org.gruppe2.game.view;

import org.gruppe2.game.controller.GameController;
import org.gruppe2.game.event.EventHandler;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.session.SessionContext;

public class GameView extends AbstractView<GameController> {

    public GameView(GameController controller) {
        super(controller);
    }

    public void onPlayerJoin(EventHandler<PlayerJoinEvent> handler) {
        getController().getSessionContext().getEventQueue().registerHandler(PlayerJoinEvent.class, handler);
    }
}
