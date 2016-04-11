package org.gruppe2.game.session;

import org.gruppe2.game.GameState;
import org.gruppe2.game.controller.AbstractPlayerController;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Session implements Runnable {
    private final MainEventQueue eventQueue = new MainEventQueue();
    private final AtomicInteger spectatorCount = new AtomicInteger(0);

    public static SessionContext start(Class<? extends Session> klass) {
        Session session;

        try {
            session = klass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

        new Thread(session).start();

        return new SessionContext(session);
    }

    MainEventQueue getEventQueue() {
        return eventQueue;
    }

    SessionContext createContext() {
        return new SessionContext(this);
    }

    GameState getGameState() {
        return GameState.EMPTY;
    }

    public AtomicInteger getSpectatorCount() {
        return spectatorCount;
    }

    public abstract boolean addPlayer(AbstractPlayerController controller);

    public abstract void addPlayerAsync(AbstractPlayerController controller);

    public abstract void exit();
}
