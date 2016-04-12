package org.gruppe2.game.session;

import org.gruppe2.game.event.Event;
import org.gruppe2.game.event.EventHandler;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.Model;
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

    public boolean addPlayer(PlayerModel model) {
        return session.addPlayer(model);
    }

    public GameModel getGameModel() { return session.getGameModel(); }

    public GameView getGameView() {
        return gameView;
    }

    public <E extends Event, M extends Model> void addEvent(Class<E> klass, M model, E event) {
        session.getEventQueue().addEvent(klass, model, event);
    }

    public <E extends Event> void addEvent(Class<E> klass, E event) {
        session.getEventQueue().addEvent(klass, event);
    }
}
