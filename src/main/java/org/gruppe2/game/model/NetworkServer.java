package org.gruppe2.game.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Created by kjors on 11.04.2016.
 */
public class NetworkServer implements Runnable {

    protected int port = 8888;
    protected ServerSocket serverSocket = null;
    protected Socket clientSocket = null;
    protected String msg = null;
    private String playerID = "Test Player";
    protected boolean notRunning = false;

    private ArrayList<Thread> threads = new ArrayList<Thread>();

    protected Thread runningThread = null;

    public NetworkServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }

        openServer();
        while(! notRunning) {
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread t = new Thread(new NetworkServerWorker(clientSocket, playerID));
            t.start();
            threads.add(t);
            System.out.println("Connection received from: " +
                    clientSocket.getRemoteSocketAddress());
        }
    }

    private synchronized boolean notRunning() {
        return this.notRunning;
    }

    public synchronized void stop() {
        this.notRunning = true;
        try {
            this.serverSocket.close();
            System.out.println("Server closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openServer() {
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        NetworkServer server = new NetworkServer(8888);
        server.run();

    }
}
