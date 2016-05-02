package org.gruppe2.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javafx.application.Platform;

import org.gruppe2.game.session.ClientSession;
import org.gruppe2.game.session.Session;
import org.gruppe2.game.session.SessionContext;
import org.gruppe2.ui.javafx.menu.Lobby;

public class MasterClient {

	ProtocolConnection connection;
	ArrayList<TableEntry> tablesInLobby = new ArrayList<TableEntry>();
	Lobby lobby;
	private static Timer sessionTimer = new Timer();

	public MasterClient(Lobby lobby) {
		this.lobby = lobby;
		connect("localhost");
		sendFirstHello();
		SetTimerTask();
		
	}

	private void SetTimerTask() {
		MasterClient THIS = this;
		sessionTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> THIS.update());
			}
		}, 0, 50);
	}

	private void connect(String ip) {
		try {
			SocketChannel channel = SocketChannel.open(new InetSocketAddress(
					ip, 8888));
			connection = new ProtocolConnection(channel);
			channel.configureBlocking(false);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		try {
			String[] message = connection.readMessage();
			
			if (message == null)
				return;
			System.out.println("recieved " + message[0]);
			switch (message[0]) {
				case "HELLO":
					if (message[1].equals("MASTER")) {
	
					}
					break;
				case "TABLE":
					System.out.println("Server sent me tables"+message);
					createTables(message);
					lobby.updateTables(tablesInLobby);
					
				case "CREATED":
					System.out.println("Client now starting context");
					createClientSessionContext();

					break;
				case "JOINED":
					createClientSessionContext();
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createTables(String[] message) {
		int i = 0;
		UUID uuid = null;
		int currentPlayers = -1;
		int maxPlayers = -1;
		for (String messagePart : message) {

			if (i % 4 == 1) {
				uuid = UUID.fromString(messagePart);
			}
			if (i % 4 == 2) {
				currentPlayers = Integer.valueOf(messagePart);
			}
			if (i % 4 == 3) {
				maxPlayers = Integer.valueOf(messagePart);
				tablesInLobby.add(new TableEntry(uuid, currentPlayers,
						maxPlayers));
			}
			
			i++;
		}

	}
	private void createClientSessionContext(){
//		SessionContext context = Session.start(ClientSession.class, connection);
//		context.waitReady();
	}

	private void sendFirstHello() {
		try {
			connection.sendMessage("HELLO\r\n");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public void createNewTable() {
		try {
			connection.sendMessage("CREATE\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void joinTable(UUID uuid) {
		try {
			connection.sendMessage("Join;"+uuid+"\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
