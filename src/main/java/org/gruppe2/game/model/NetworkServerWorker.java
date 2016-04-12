package org.gruppe2.game.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by kjors on 11.04.2016.
 */
public class NetworkServerWorker implements Runnable{

    protected Socket clientSocket = null;
    protected String serverName = null;

    public NetworkServerWorker(Socket clientSocket, String name) {
        this.clientSocket = clientSocket;
        this.serverName = name;
    }

    @Override
    public void run() {

        try {
            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();

            /**
             * Message logic goes here. Returns based on protocoll
             * [playerID;action;message] as string
             *
             * action = chat, move
             *
             * ex   2;chat;this is a message - post message to chatwindow
             *
             * ex   3;move;bet 30 - means player 3 bets 30
             *      3;move;h14 c2 - player 3 pocket is ace of hearts and 2 of clover
             *      table;move;pot 0 - table removes pot
             *      7;move;stack 300 - player 7 wins 300 chips
             *      9;move;loose - player 9 loose
              */


            out.write(("This is a test message from server").getBytes());
            out.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
