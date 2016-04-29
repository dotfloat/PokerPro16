package org.gruppe2.network;

import org.gruppe2.network.NetworkClient;
import org.gruppe2.network.NetworkServer;
import org.gruppe2.ui.javafx.menu.Lobby;

/**
 * Tester for lan server
 */
public class NetworkTester {
	public static Lobby lobby; //Remove this when handler is ready!
	public static boolean lanTest = true;
	
	public static void main(String[] args) {
		testNetwork(null);
	}

	public static void testNetwork(Lobby lobbyFrom) {
		lobby = lobbyFrom;
		System.out
				.println("Read network protocol.txt in git folder for description");
		if(lanTest){
			NetworkServer server = new NetworkServer(8888);
			Thread serverThread = new Thread(server);
			System.out.println("server start");
			serverThread.start();
		}
		
		NetworkClient client = new NetworkClient();
		Thread clientThread = new Thread(client);
		System.out.println("client start");
		clientThread.start();
	}
}
