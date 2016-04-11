package org.gruppe2.game.session;

import org.gruppe2.game.controller.AbstractPlayerController;
import org.gruppe2.game.controller.GameController;
import org.gruppe2.game.model.GameModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameSession extends Session {
    private final List<AbstractPlayerController> players = Collections.synchronizedList(new ArrayList<>());
    private final int maxPlayers;
    private final GameController gameController;

    private boolean playing = true;

    public GameSession(int maxPlayers) {
        this.maxPlayers = maxPlayers;
        this.gameController = new GameController(getSessionContext(), new GameModel(UUID.randomUUID()));
}

    @Override
    public void update() {
        this.gameController.update();
    }

    public boolean isPlaying() {
        return playing;
    }

    @Override
    public boolean addPlayer(AbstractPlayerController controller) {
        synchronized (players) {
            if (players.size() >= maxPlayers) {
                return false;
            }

            players.add(controller);

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