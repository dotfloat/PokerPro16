package org.gruppe2.game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class GameModel implements Model {
    private final UUID uuid;
    private final List<PlayerModel> players;

    public GameModel(UUID uuid) {
        this.uuid = uuid;
        this.players = Collections.synchronizedList(new ArrayList<>());
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<PlayerModel> getPlayers() {
        return players;
    }
}
