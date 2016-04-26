package org.gruppe2.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkServerGameSession {
	
	private Socket clientSocket = null;
	private String serverGameName = null;
	ArrayList<Socket> clients = null;
	ArrayList<PrintWriter> outs;
	ArrayList<BufferedReader> ins;
	NetworkServerGameSession(Socket clientSocket, String name, ArrayList<Socket> clients ) {
		this.clientSocket = clientSocket;
		this.serverGameName = name;
		this.clients = clients;
		startGame();
	}
	
	
	public void startGame(){
		try {
			String input;

			while (true) {
				PrintWriter out = new PrintWriter(
						clientSocket.getOutputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));
				input = in.readLine();
				System.out.println("Data received: " + input);
				if (input == null) {
					break;
				}
				String[] s = input.split(";");

				if (s[0].equals("bye")) {
					System.out.println("Canceling connection");
					break;
				}
				

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
			
			//guiClient.setAction(new Action.Raise(betValue));
//			for each player(send 1;move;raise value)
		}
		if (message.contains("call")) {

			System.out.println("Player: " + playerID + " call");
			//guiClient.setAction(new Action.Call());
		}
		if (message.contains("check")) {
			System.out.println("Player: " + playerID + " check");
			//guiClient.setAction(new Action.Check());
		}
		if (message.contains("fold")) {
			System.out.println("Player: " + playerID + " folded");
			
			//guiClient.setAction(new Action.Fold());
		}
	}

	private String getPlayerID(String[] s) {
		if (s.length == 0) {
			System.out.println("Error in net protocol");
			System.exit(1);
		}
		return s[0];
	}

	private String getAction(String[] s) {
		if (s.length == 1) {
			System.out.println("Error in net protocol");
			System.exit(1);
		}
		return s[1];
	}

	private String getMessage(String[] s) {
		if (s.length == 2) {
			System.out.println("Error in net protocol");
			System.exit(1);
		}

		return s[2];

	}

}
