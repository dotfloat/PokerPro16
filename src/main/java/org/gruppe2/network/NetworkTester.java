package org.gruppe2.network;

import org.gruppe2.network.NetworkClient;
import org.gruppe2.network.NetworkServer;

/**
 * Created by kjors on 12.04.2016.
 */
public class NetworkTester {
    public static void main(String[] args) {
        NetworkServer server = new NetworkServer(8888);
        NetworkClient client = new NetworkClient();

        server.run();

        client.start();

    }
}
