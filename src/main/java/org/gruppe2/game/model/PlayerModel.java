package org.gruppe2.game.model;

import java.util.UUID;

public class PlayerModel {
    private final UUID uuid;
    private final String name;
    private final String avatar;

    public PlayerModel(UUID uuid, String name, String avatar) {
        this.uuid = uuid;
        this.name = name;
        this.avatar = avatar;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }
}
