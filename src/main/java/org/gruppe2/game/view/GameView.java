package org.gruppe2.game.view;

import org.gruppe2.game.event.EventHandler;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.session.SessionContext;

public class GameView extends AbstractView<GameModel> {

    public void onPlayerJoin(EventHandler<PlayerJoinEvent> handler) {
    }

    public PlayerView getPlayerByName(String name) {

        return null;
    }
}
