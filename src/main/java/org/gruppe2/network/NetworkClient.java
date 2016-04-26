package org.gruppe2.network;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkClient implements Runnable {

	private int port = 8888;
	private String host = "localhost";

	@Override
	public void run() {

		try (Socket socket = new Socket(host, port);
				PrintWriter out = new PrintWriter(socket.getOutputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				BufferedReader stdIn = new BufferedReader(
						new InputStreamReader(System.in));) {
			// create socket-object ?
			String initialMessage = "master";
            String secondMessage = "ok";
            String joinMessage = "join";

            while (true) {
                String fromServer = in.readLine();
                if (fromServer == null) {
                    System.out.println("Client: " + initialMessage);
                    out.println(initialMessage + "\n");
                    out.flush();
                }
                else if(fromServer.equals("yes")) {
                    System.out.println("Client: " + secondMessage);
                    out.println(secondMessage + "\n");
                    out.flush();
                }
                else if(fromServer.contains("table")){
                    String[] s = fromServer.split(";");
                    joinMessage = joinMessage + " " + s[0];
                    System.out.println("Client: " + joinMessage);
                    out.println(joinMessage + "\n");
                    out.flush();
                }
                else if(fromServer.contains("ok") && fromServer.contains("join")) {
                    String[] s = fromServer.split(";");
                    join(Integer.parseInt(s[2]));
                    break;
                }
            }


		} catch (UnknownHostException h) {
			h.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

    public void onServerConnect() {

    }

    public void onGameStart() {

    }

    public void join(int table) {

    }
}
