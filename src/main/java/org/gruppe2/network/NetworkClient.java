package org.gruppe2.network;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkClient implements Runnable {

	private int port = 8888;
	private String host = "localhost";
	boolean firstMessage = true;
	boolean inGame = false;
	
	String initialMessage = "master";
    String secondMessage = "ok";
    String joinMessage = "join";
    
	@Override
	public void run() {

		try (Socket socket = new Socket(host, port);
				PrintWriter out = new PrintWriter(socket.getOutputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				BufferedReader stdIn = new BufferedReader(
						new InputStreamReader(System.in));) {

			
            while (true) {
            	if(!inGame)
            		onServerConnect(out,in);
            	else
            		onGameStart();
            }

		} catch (UnknownHostException h) {
			h.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Before you are in a game, you must choose what to do, create table, or join a specific table
	 * @param out
	 * @param in
	 * @throws IOException
	 */
    public void onServerConnect(PrintWriter out, BufferedReader in) throws IOException {
    	if (firstMessage) {
            System.out.println("Client: " + initialMessage);
            out.println(initialMessage + "\n");
            out.flush();
            firstMessage = false;
        }
        String fromServer = in.readLine();
        
        if(fromServer.equals("yes")) {
            System.out.println("Client: " + secondMessage);
            out.println(secondMessage + "\n");
            out.flush();
        }
        else if(fromServer.contains("table")){
            String[] s = fromServer.split(";");
            joinMessage = joinMessage + ";" + s[1];
            System.out.println("Client: " + joinMessage);
            out.println(joinMessage + "\n");
            out.flush();
        }
        else if(fromServer.contains("ok") && fromServer.contains("join")) {
            String[] s = fromServer.split(";");
            System.out.println("spillet er klart!");
            join(Integer.parseInt(s[2]));
            inGame = true;
        }
    }

    public void onGameStart() {
    	
    	System.out.println("client ingame jippi!");
    }

    public void join(int table) {

    }
}
