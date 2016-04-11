package org.gruppe2.game.session;

import org.gruppe2.game.controller.AbstractPlayerController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameSession extends Session {
    private final List<AbstractPlayerController> players = Collections.synchronizedList(new ArrayList<>());
    private final int maxPlayers;

    private boolean playing = true;

    public GameSession(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    @Override
    public void update() {

    }

    public boolean isPlaying() {
        return playing;
    }

    @Override
    public boolean addPlayer(AbstractPlayerController controller) {
        synchronized (players) {
            if (players.size() >= maxPlayers) {
                controller.setAsyncStatus(AsyncStatus.FAILED);
                return false;
            }

            players.add(controller);

            controller.setAsyncStatus(AsyncStatus.COMPLETED);
            return true;
        }
    }

    @Override
    public void exit() {
        playing = false;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }
}