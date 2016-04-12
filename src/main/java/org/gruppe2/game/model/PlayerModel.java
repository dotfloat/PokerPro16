package org.gruppe2.game.model;

import java.util.UUID;

public class PlayerModel implements Model {
    private final UUID uuid;
    private final String name;
    private final String avatar;
    private volatile int bank;

    public PlayerModel(UUID uuid, String name, String avatar) {
        this.uuid = uuid;
        this.name = name;
        this.avatar = avatar;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }
}
