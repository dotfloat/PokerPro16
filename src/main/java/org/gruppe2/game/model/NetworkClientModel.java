package org.gruppe2.game.model;

import org.gruppe2.network.ProtocolConnection;

public class NetworkClientModel {
    private final ProtocolConnection connection;

    public NetworkClientModel(ProtocolConnection connection) {
        this.connection = connection;
    }

    public ProtocolConnection getConnection() {
        return connection;
    }
}
