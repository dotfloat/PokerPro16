package org.gruppe2.game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class GameModel implements Model {
    private final UUID uuid;
    private final int maxPlayers;
    private final List<PlayerModel> players;

    private volatile boolean waitingForPlayers = false;

    public GameModel(UUID uuid, int maxPlayers) {
        this.uuid = uuid;
        this.maxPlayers = maxPlayers;
        this.players = Collections.synchronizedList(new ArrayList<>());
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<PlayerModel> getPlayers() {
        return players;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public PlayerModel getPlayerByUUID(UUID uuid) {
        for (PlayerModel player : players) {
            if (player.getUUID().equals(uuid))
                return player;
        }

        return null;
    }

    public PlayerModel getPlayerByName(String name) {
        for (PlayerModel player : players) {
            if (player.getName().equals(name))
                return player;
        }

        return null;
    }

    public boolean isWaitingForPlayers() {
        return waitingForPlayers;
    }

    public void setWaitingForPlayers(boolean waitingForPlayers) {
        this.waitingForPlayers = waitingForPlayers;
    }
}
