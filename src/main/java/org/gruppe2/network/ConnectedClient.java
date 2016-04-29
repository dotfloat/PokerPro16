package org.gruppe2.network;

import java.util.UUID;

public class ConnectedClient {
    private final ProtocolConnection connection;
    private UUID playerUUID = null;

    public ConnectedClient(ProtocolConnection connection) {
        this.connection = connection;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public ProtocolConnection getConnection() {
        return connection;
    }
}
