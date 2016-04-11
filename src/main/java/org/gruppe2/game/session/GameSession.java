package org.gruppe2.game.session;

import org.gruppe2.game.controller.AbstractPlayerController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameSession extends Session {
    private List<AbstractPlayerController> players = new ArrayList<AbstractPlayerController>();
    private ConcurrentLinkedQueue<AbstractPlayerController> newPlayers = new ConcurrentLinkedQueue<>();
    private boolean playing = true;
    private int maxPlayers = 10;

    @Override
    public void run() {
        while(playing) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    @Override
    public boolean addPlayer(AbstractPlayerController controller) {
        synchronized (players) {
            if (players.size() + 1 < maxPlayers) {
                controller.setAsyncStatus(AsyncStatus.REFUSED);
                return false;
            }

            players.add(controller);

            controller.setAsyncStatus(AsyncStatus.COMPLETED);
            return true;
        }
    }

    @Override
    public void addPlayerAsync(AbstractPlayerController controller) {

    }

    @Override
    public void exit() {
        playing = false;
    }
}