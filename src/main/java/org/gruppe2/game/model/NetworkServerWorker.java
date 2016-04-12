package org.gruppe2.game.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

        try
        {

            //  OutputStream out = clientSocket.getOutputStream();


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
            String input, output;

            System.out.println("utenfor while");


            while(true) {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                input = in.readLine();
                System.out.println("Data received: " + input);
                String[] s = input.split(";");
                if(s[1].equals("chat")) {
                    output = "fikk chat";
                    out.println(output);
                    System.out.println("chat");

                }
                if(s[1].equals("bye")) {
                    break;
                }
            }

          //  out.close();
          //  in.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
