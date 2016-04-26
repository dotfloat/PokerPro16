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
			String fromServer;
			String fromUser;

			while ((fromUser = stdIn.readLine()) != null) {
				if (fromUser != null) {
					System.out.println("Client: " + fromUser);
					out.println(fromUser + "\n");
					out.flush();
				}
			}

		} catch (UnknownHostException h) {
			h.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
