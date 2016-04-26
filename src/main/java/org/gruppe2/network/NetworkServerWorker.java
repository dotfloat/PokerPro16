package org.gruppe2.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import org.gruppe2.game.Action;

class NetworkServerWorker implements Runnable {

	private Socket clientSocket = null;
	private String serverName = null;
	boolean masterGreeting = false;
	boolean masterGreetingOK = false;
	boolean gameStarted = false;

	NetworkServerWorker(Socket clientSocket, String name) {
		this.clientSocket = clientSocket;
		this.serverName = name;
		System.out.println("hei");
		
	}

	
	@Override
	public void run() {
		System.out.println("jeg er i server");
		try {
			String input;
			
			while (true) {
				PrintWriter out = new PrintWriter(
						clientSocket.getOutputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));
				input = in.readLine();
				
				if (input == null) {
					break;
				}
				String[] s = input.split(";");
				
				if (s[0].equals("bye")) {
					System.out.println("Canceling connection");
					out.print("bye");
					break;
				}
				
				if(masterGreetingOK == true && masterGreetingOK== true){ //Thrid print starts game
					if(s[0].equals("join")){
						if(s[1].equals("1"))
							out.print("ok;join;1\n");
							out.flush();
							System.out.println("ok;join;1");
							ArrayList<Socket> clients = new ArrayList<Socket>();
							clients.add(clientSocket);
							gameStarted = true;
							new NetworkServerGameSession(clientSocket,in,out,"new Game",clients,6);
					}
				}
				
				if(masterGreetingOK == false && masterGreeting== true && input.equals("ok")){ //Second print gives tables
					out.print("table;1;4;6\n");
					out.flush();
					System.out.println("table;1;4;6");
					masterGreetingOK = true;
				}
				
				if(masterGreeting== false && input.equals("master")){ //First print on master request
					masterGreeting = true;
					System.out.println("yes i server");
					out.print("yes\n");
					out.flush();
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

	

	

}
