package org.gruppe2.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javafx.application.Platform;

import org.gruppe2.game.session.ClientSession;
import org.gruppe2.game.session.Session;
import org.gruppe2.game.session.SessionContext;
import org.gruppe2.ui.javafx.menu.Lobby;

/**
 * Class for client handling in lobby, controll is given to ClientController when the game starts
 *
 * @author htj063
 */
public class MasterClient {

    private NetworkIO connection;
    private ArrayList<TableEntry> tablesInLobby = new ArrayList<>();
    private Lobby lobby;
    private static Timer sessionTimer = new Timer();
    private static String ip = "localhost";

    public MasterClient(Lobby lobby) {
        this.lobby = lobby;
        connect(ip);
        System.out.println(connection);
        if (connection != null) {
            sendFirstHello();
            SetTimerTask();
        }
    }

    private void SetTimerTask() {
        MasterClient THIS = this;
        sessionTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(THIS::update);
            }
        }, 0, 50);
    }

    private void connect(String ip) {
        try {
            SocketChannel channel = SocketChannel.open(new InetSocketAddress(
                    ip, 8888));
            connection = new NetworkIO(channel);

            channel.configureBlocking(false);

            connection.setInputFormat(NetworkIO.Format.STRING);
            connection.setOutputFormat(NetworkIO.Format.STRING);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        try {
            String[] message = connection.readMessage();

            if (message == null)
                return;
            System.out.println("Client recieved " + message[0]);

            switch (message[0]) {
                case "HELLO":
                    if (message[1].equals("MASTER")) {
                        System.out.println("You are now connected to master server");
                    }
                    break;
                case "TABLE":
                    System.out.println("table is:" + message[0] + " " + message[1]);
                    createTables(message);
                    lobby.updateTables(tablesInLobby);
                    break;
                case "CREATED":
                    lobby.createGame();

                    break;
                case "JOINED":
                    lobby.joinGame();
                    break;
                case "NO":
                    System.out.println("could not join that table");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createTables(String[] message) {
        int i = 0;
        UUID uuid = null;
        String name = null;
        int currentPlayers = -1;
        int maxPlayers = -1;
        for (String messagePart : message) {

            if (i % 5 == 1) {
                uuid = UUID.fromString(messagePart);
            } else if (i % 5 == 2) {
                name = messagePart;
            } else if (i % 5 == 3) {
                currentPlayers = Integer.valueOf(messagePart);
            } else if (i % 5 == 4) {
                maxPlayers = Integer.valueOf(messagePart);
                tablesInLobby.add(new TableEntry(uuid, name, currentPlayers, maxPlayers));
            }

            i++;
        }
    }

    private void sendFirstHello() {
        try {
            connection.sendMessage("HELLO\r\n");
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * Asks server if you can create new table
     *
     * @param uuid
     */
    public void requestCreateGame() {
        try {
            connection.sendMessage("CREATE\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Joins table that was asked to be created
     *
     * @return
     */
    public SessionContext createNewTable() {
        sessionTimer.cancel();

        return Session.start(ClientSession.class, connection);
    }

    /**
     * Asks server if you can join table with specific uuid
     *
     * @param uuid
     */
    public void requestJoinTable(UUID uuid) {
        try {
            connection.sendMessage("JOIN TABLE;" + uuid + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Joins table that was asked to be joined
     *
     * @return
     */
    public SessionContext joinTable() {
        sessionTimer.cancel();

        return Session.start(ClientSession.class, connection);
    }

    /**
     * Test if server is up, so we dont need to start lobby if it is not.
     * creates a client, and instantly remove it, to check
     *
     * @return
     */
    public static boolean localMasterServerIsUp() {
        try {
            SocketChannel channel = SocketChannel.open(new InetSocketAddress(
                    ip, 8888));
            NetworkIO testConnection = new NetworkIO(channel);
            testConnection.sendMessage("BYE\r\n");
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    public static boolean onlineMasterServerIsUp() {
        try {
            ip = "zohar.no";
            SocketChannel channel = SocketChannel.open(new InetSocketAddress(
                    ip, 8888));
            NetworkIO testConnection = new NetworkIO(channel);
            testConnection.sendMessage("BYE\r\n");
            return true;

        } catch (IOException e) {
            return false;
        }
    }


}
