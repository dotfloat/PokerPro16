package org.gruppe2.network;

import org.gruppe2.network.NetworkClient;
import org.gruppe2.network.NetworkServer;

/**
 * Tester for lan server
 */
public class NetworkTester {

	public static void main(String[] args) {
		testNetwork();
	}

	public static void testNetwork() {
		System.out
				.println("Read network protocol.txt in git folder for description");

		NetworkServer server = new NetworkServer(8888);
		Thread serverThread = new Thread(server);
		System.out.println("server start");
		serverThread.start();

		NetworkClient client = new NetworkClient();
		Thread clientThread = new Thread(client);
		System.out.println("client start");
		clientThread.start();
	}
}
