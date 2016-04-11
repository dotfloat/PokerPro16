package org.gruppe2.game.session;

import org.gruppe2.game.GameState;
import org.gruppe2.game.controller.PlayerController;
import org.gruppe2.game.event.EventHandler;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.view.GameView;

public class SessionContext {
    private Session session;

    private ConcurrentEventQueue eventQueue = new ConcurrentEventQueue();

    private GameView gameView = new GameView(this);

    SessionContext(Session session) {
        this.session = session;
        this.session.getEventQueue().registerQueue(eventQueue);
    }

    public ConcurrentEventQueue getEventQueue() {
        return eventQueue;
    }

    public GameState getGameState() {
        return session.getGameState();
    }

    public boolean addPlayer(PlayerModel model, EventHandler<PlayerActionQuery> handler) {
        return session.addPlayer(model, handler);
    }

    public GameView getGame() {
        return gameView;
    }
}
