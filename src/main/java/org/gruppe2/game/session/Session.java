package org.gruppe2.game.session;

import org.gruppe2.game.GameState;
import org.gruppe2.game.controller.PlayerController;
import org.gruppe2.game.event.EventHandler;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.model.PlayerModel;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Session implements Runnable {
    private final MainEventQueue eventQueue = new MainEventQueue();
    private final AtomicInteger spectatorCount = new AtomicInteger(0);

    private final SessionContext sessionContext = new SessionContext(this);

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

        return session.start();
    }

    public SessionContext start() {
        new Thread(this).start();

        return new SessionContext(this);
    }

    @Override
    public void run() {
        while(true) {

            eventQueue.process();

            update();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    public abstract void update();

    public abstract void exit();

    // TODO: Move these to a "GameController" or something

    public abstract boolean addPlayer(PlayerModel model, EventHandler<PlayerActionQuery> handler);

    public SessionContext getSessionContext() {
        return sessionContext;
    }
}
