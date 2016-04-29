package org.gruppe2.network;

import java.nio.channels.SocketChannel;
import java.util.UUID;

public class ConnectedClient {
    private final SocketChannel channel;
    private UUID playerUUID = null;

    public ConnectedClient(SocketChannel channel) {
        this.channel = channel;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public SocketChannel getChannel() {
        return channel;
    }
}
