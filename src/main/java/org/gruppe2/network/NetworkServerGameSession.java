package org.gruppe2.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import org.gruppe2.Main;
import org.gruppe2.game.GameBuilder;
import org.gruppe2.game.session.SessionContext;
import org.gruppe2.ui.javafx.InGame;;

public class NetworkServerGameSession {
	private static SessionContext context = null;
	
	private Socket clientSocket = null;
	private String serverGameName = null;
	int maxPlayers;
	ArrayList<Socket> clients = null;
	ArrayList<PrintWriter> outs;
	ArrayList<BufferedReader> ins;
	BufferedReader FromOrganizerIn;
	PrintWriter ToOrganizerOut;
	public static boolean playerHasStartedGame = false;
	
	NetworkServerGameSession(Socket clientSocket, BufferedReader in, PrintWriter out, String name, ArrayList<Socket> clients, int maxPlayers) {
		this.clientSocket = clientSocket;
		this.serverGameName = name;
		this.clients = clients;
		this.maxPlayers = maxPlayers;
		FromOrganizerIn = in;
		ToOrganizerOut = out;
		NetworkServer.gamesOnServer.add(this);
		startGame();
	}

	public void startGame(){
		
		startGameLoop();
		try {
			String input;

			while (true) {
				System.out.println("server inside gameloop, ready");
				BufferedReader in = FromOrganizerIn;
				PrintWriter out = ToOrganizerOut;
				input = in.readLine();
				System.out.println("Data received: " + input);
				if (input == null) {
					break;
				}
				String[] s = input.split(";");

				if (s[0].equals("bye")) {
					sendPlayerDisconnect(s);
					
					break;
				}
				if(s.length == 1)
					continue;
				

				String playerID = getPlayerID(s);
				String action = getAction(s);
				String message = getMessage(s);

				if (action.equals("chat")) {
					sendChatMessage(playerID, message, out);
				} else if (action.equals("move")) {
					sendMoveMessage(playerID, message, out);
				}
			}
			clientSocket.close();
			NetworkServer.removeThread(Thread.currentThread());
			Thread.currentThread().interrupt();

		} catch (IOException e) {
			System.out.println("Test");
			e.printStackTrace();
		}
		System.out.println("game ended");
	}
	private void sendPlayerDisconnect(String[] s) {
		System.out.println("Canceling connection for player XX");
		context.message("sendMessageToClients", "join","XX;bye");
	}

	private void startGameLoop() {
		System.out.println("setting server game session");
		context = new GameBuilder().networkStart();
		UUID player1UUID = UUID.randomUUID();
		InGame.setPlayerUUID(player1UUID);
		InGame.setContext(context);
		
        context.waitReady();
        System.out.println("Server gamesession init finished");
        
        context.message("addPlayer", player1UUID, "TestPlayer", "default");
        context.message("addPlayerStatistics", UUID.randomUUID(), Main.loadPlayerStatistics());
       
        context.setAnnotated(this);
		
	}

	/**
	 * Send chat message
	 * 
	 * @param playerID
	 * @param message
	 * @param out
	 */
	private void sendChatMessage(String playerID, String message,
			PrintWriter out) {
		String output = "chat recieved";
		out.println(output);
		context.message("sendMessageToClients", "join","1;chat;halla gutta");
		System.out.println("Player " + playerID + ": " + message);
	}
	
	/**
	 * 
	 * @param playerID
	 * @param message
	 * @param out
	 */
	private void sendMoveMessage(String playerID, String message,
			PrintWriter out) {
		String output = "move recieved";
		int playerNumber = Integer.valueOf(playerID);
		out.println(output);
		
		if (message.contains("raise")) {
			int betValue = Integer.valueOf(message.substring(6));
			System.out.println("Player: " + playerID + " raise" + betValue);
			context.message("sendMessageToClients", "join","1;move;raise 30");//for each player(send 1;move;raise value)
			

		}
		if (message.contains("call")) {	
			System.out.println("Player: " + playerID + " call");
			context.message("sendMessageToClients", "join","1;move;call");
			//guiClient.setAction(new Action.Call());
		}
		if (message.contains("check")) {
			System.out.println("Player: " + playerID + " check");
			context.message("sendMessageToClients", "join","1;move;check");
			//guiClient.setAction(new Action.Check());
		}
		if (message.contains("fold")) {
			System.out.println("Player: " + playerID + " folded");
			context.message("sendMessageToClients", "join","1;move;fold");
			//guiClient.setAction(new Action.Fold());
		}
	}

	private String getPlayerID(String[] s) {
		if (s.length == 0) {
			System.out.println("Error in net protocol size 0");
			System.exit(1);
		}
		return s[0];
	}

	private String getAction(String[] s) {
		if (s.length == 1) {
			System.out.println("Error in net protocol size 1");
			System.exit(1);
		}
		return s[1];
	}

	private String getMessage(String[] s) {
		if (s.length == 2) {
			System.out.println("Error in net protocol size 2");
			System.exit(1);
		}
		return s[2];
	}
	public String getServerGameName(){
		return serverGameName;
	}

}
