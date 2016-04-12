package org.gruppe2.game.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by kjors on 11.04.2016.
 */
public class NetworkClient {

    private int port = 8888;
    private String host = "localhost";


    public String send(String outMsg) {

        String inMsg = null;

        try (
            Socket socket = new Socket(host, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            // create socket-object ?

            // just commit test
            inMsg = "Dette er en test";
        } catch (UnknownHostException h) {
            h.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inMsg;
    }
}
