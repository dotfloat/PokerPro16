package org.gruppe2.game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class GameModel {

    public enum BotPolicy { FILL }

    private final UUID uuid;
    private final int minPlayers;
    private final int maxPlayers;
    private final List<PlayerModel> players = Collections.synchronizedList(new ArrayList<>());
    private final int buyIn;
    private final BotPolicy botPolicy;

    private volatile int button = 0;
    private volatile boolean waitingForPlayers = false;

    public GameModel(UUID uuid, int minPlayers, int maxPlayers, int buyIn, BotPolicy botPolicy) {
        this.uuid = uuid;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.buyIn = buyIn;
        this.botPolicy = botPolicy;
    }

    public UUID getUUID() {
        return uuid;
    }

    public List<PlayerModel> getPlayers() {
        return players;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public boolean isWaitingForPlayers() {
        return waitingForPlayers;
    }

    public void setWaitingForPlayers(boolean waitingForPlayers) {
        this.waitingForPlayers = waitingForPlayers;
    }

    public int getButton() {
        return button;
    }

    public void setButton(int button) {
        this.button = button;
    }

    public BotPolicy getBotPolicy() {
        return botPolicy;
    }

    public int getBuyIn() {
        return buyIn;
    }
}
