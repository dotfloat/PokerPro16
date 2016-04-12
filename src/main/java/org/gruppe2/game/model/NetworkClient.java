package org.gruppe2.game.model;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by kjors on 11.04.2016.
 */
public class NetworkClient {

    private int port = 8888;
    private String host = "localhost";


    public String start() {

        String inMsg = null;

        try (
            Socket socket = new Socket(host, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        ) {
            // create socket-object ?
            String fromServer;
            String fromUser;


                fromUser = stdIn.readLine();
                if(fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }



        } catch (UnknownHostException h) {
            h.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inMsg;
    }

    public static void main(String[] args) {
        NetworkClient client = new NetworkClient();
        client.start();
    }
}
