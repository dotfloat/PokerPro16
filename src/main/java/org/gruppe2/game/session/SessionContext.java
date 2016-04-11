package org.gruppe2.game.session;

import org.gruppe2.game.GameState;
import org.gruppe2.game.controller.AbstractPlayerController;
import org.gruppe2.game.view.GameView;

public class SessionContext {
    private Session session;

    private ConcurrentEventQueue eventQueue = new ConcurrentEventQueue();

    SessionContext(Session session) {
        this.session = session;
        this.session.getEventQueue().registerQueue(eventQueue);
    }

    public ConcurrentEventQueue getEventQueue() {
        return eventQueue;
    }

    /**
     * Get the number of spectators that are connected
     * @return spectator count
     */
    public int getSpectatorCount() {
        return session.getSpectatorCount().get();
    }

    /**
     * Add a new spectator (ie. increment the count)
     * @return old spectator count
     */
    public int addSpectator() {
        return session.getSpectatorCount().getAndIncrement();
    }

    /**
     * Remove a spectator (ie. decrement the count)
     * @return old spectator count
     */
    public int removeSpectator() {
        return session.getSpectatorCount().getAndDecrement();
    }

    public GameState getGameState() {
        return session.getGameState();
    }

    public boolean addPlayer(AbstractPlayerController controller) {
        return session.addPlayer(controller);
    }

    public int getMaxPlayers() {
        return session.getMaxPlayers();
    }

    public GameView getGame() {
        return session.getGame();
    }
}
