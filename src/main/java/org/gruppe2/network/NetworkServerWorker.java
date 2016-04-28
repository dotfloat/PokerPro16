package org.gruppe2.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

class NetworkServerWorker implements Runnable {

	private Socket clientSocket = null;
	private String serverName = null;
	boolean masterGreeting = false;
	boolean masterGreetingOK = false;
	boolean gameStarted = false;
	NetworkServer networkServer = null;
    private PrintWriter outWriter;
    private BufferedReader inReader;

    NetworkServerWorker(Socket clientSocket, String name, NetworkServer networkServer) {
		this.networkServer = networkServer;
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
                setIn(out, in);
                System.out.println("waiting for input");
				input = in.readLine();
				
				if (input == null) {
					System.out.println("break input from client");
					break;
				}
				String[] s = input.split(";");
				
				if (s[0].equals("bye")) {
					System.out.println("Canceling connection");
					out.print("bye");
					break;
				}
				
				if(masterGreeting == true && masterGreetingOK== true){ //Thrid print starts game
					if(s[0].equals("join")){
						if(tableIsJoinable(s)){
							out.println("ok;join;"+s[1]);
							out.flush();
							System.out.println("ok;join;"+s[1]);
							networkServer.getTables().get(Integer.valueOf(s[1])).clients.add(clientSocket);
							gameStarted = true;
						}
					}
                    else if(s[0].equals("create")) {
                        createTable(clientSocket, in, out);
                    }

				}
				System.out.println("ok="+masterGreetingOK+"master="+masterGreeting+"input="+input.equals("ok"));
				if(masterGreetingOK == false && masterGreeting== true && input.equals("ok")){ //Second print gives tables
					
					out.print(createTablesString());
					out.flush();
					System.out.println(createTablesString());
					masterGreetingOK = true;
				}
				
				if(masterGreeting== false && input.equals("master")){ //First print on master request
					masterGreeting = true;
					System.out.println("yes i server");
					out.println("yes");
					out.flush();
				}

			}
			System.out.println("ended");
			clientSocket.close();
			NetworkServer.removeThread(Thread.currentThread());
			Thread.currentThread().interrupt();

		} catch (IOException e) {
			System.out.println("Test");
			e.printStackTrace();
		}

	}

    private void setIn(PrintWriter out, BufferedReader in) {
        this.outWriter = out;
        this.inReader = in;
    }


    private boolean tableIsJoinable(String[] s) {
		int tableNumber = Integer.valueOf(s[1]);
		System.out.println("in check for table size");
		if (networkServer.getTables().size() >= tableNumber){
			if(networkServer.getTables().get(tableNumber).ins.size() < networkServer.getTables().get(tableNumber).maxPlayers){
				return true;
			}
		}
		return false;
	}


	private String createTablesString() {
		System.out.println("creating tables");
		String tableString = "";
		int tableNumber = 1;
		for(NetworkServerGameSession table : networkServer.getTables()){
			if(tableNumber>1)
				tableString.concat(";");
			tableString.concat("table;"+tableNumber+";"+table.ins.size()+";"+table.maxPlayers);
			tableNumber++;
		}
		return tableString;
	}


    public void createTable(Socket clientSocket, BufferedReader in, PrintWriter out) {
        String name = "table";
        ArrayList<Socket> clients = new ArrayList<Socket>();
        NetworkServerGameSession table = new NetworkServerGameSession(clientSocket, in, out, name, clients, 6);
    }
}
