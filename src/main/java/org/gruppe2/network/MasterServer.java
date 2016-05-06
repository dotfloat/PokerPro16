package org.gruppe2.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.UUID;

import org.gruppe2.game.GameBuilder;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.session.SessionContext;

/**
 * Class for handling lobby requests from clients, server gives controll to ServerController when the game starts.
 *
 * @author htj063
 */
public class MasterServer {
    ArrayList<SessionContext> activeTables = new ArrayList<SessionContext>();
    ServerSocketChannel serverSocket;
    private ArrayList<NetworkIO> clients = new ArrayList<>();

    public MasterServer() {
        startServer();
        while (true) {
            update();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void startServer() {
        try {
            serverSocket = ServerSocketChannel.open();
            serverSocket.socket().bind(new InetSocketAddress(8888));
            serverSocket.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (serverSocket != null) {
            try {
                SocketChannel client = serverSocket.accept();

                if (client != null) {
                    NetworkIO connection = new NetworkIO(
                            client);
                    client.configureBlocking(false);
                    clients.add(connection);
                }

            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        for (int i = 0; i < clients.size(); i++) {
            try {
                String[] args = clients.get(i).readMessage();

                if (args == null)
                    continue;
                switch (args[0]) {

                    case "HELLO":
                        clients.get(i).sendMessage("HELLO;MASTER\r\n");
                        clients.get(i).sendMessage(createTableString() + "\r\n");
                        break;
                    case "JOIN TABLE":

                        if (canJoinTable(args[1])) {
                            clients.get(i)
                                    .sendMessage("JOINED;" + args[1] + "\r\n");
                            connectClientToTable(clients.get(i), args[1]);
                            clients.remove(i--);
                        } else
                            clients.get(i).sendMessage("NO\r\n");

                        break;
                    case "CREATE":
                        clients.get(i).sendMessage("CREATED\r\n");

                        SessionContext context = new GameBuilder().start();

                        context.waitReady();
                        Thread.sleep(100);
                        context.message("addClient", clients.get(i));

                        activeTables.add(context);
                        clients.remove(i--);

                        break;
                    case "BYE":
                        clients.remove(i--);
                        break;

                }
            } catch (IOException e) {
                clients.remove(i--);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private boolean canJoinTable(String tableUUID) {
        int tableNumber = getTableNumberFromUUID(tableUUID);
        if (tableNumber == -1) {
            return false;
        }

        if (activeTables.size() >= tableNumber) {
            int maxPlayers = activeTables.get(tableNumber)
                    .getModel(GameModel.class).getMaxPlayers();
            int currentPlayers = (int) activeTables.get(tableNumber)
                    .getModel(GameModel.class).getPlayers().stream().filter(p -> !p.isBot()).count();
            if (currentPlayers < maxPlayers) {
                return true;
            }
        }
        return false;
    }

    private int getTableNumberFromUUID(String tableUUID) {
        int index = 0;
        for (SessionContext table : activeTables) {
            UUID uuid = table.getModel(GameModel.class).getUUID();
            if (uuid.toString().equals(tableUUID)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private void connectClientToTable(NetworkIO client,
                                      String tableUUID) {
        int tableNumber = getTableNumberFromUUID(tableUUID);

        activeTables.get(tableNumber).message("addClient", client);
    }

    public void removeTableFromList(SessionContext leavingTable) {
        System.out.println("removed table from list");
        activeTables.remove(leavingTable);
    }

    public String createTableString() {
        String tableString = "";
        int tableNumber = 0;
        for (SessionContext table : activeTables) {
            if (tableNumber == 0)
                tableString = tableString.concat("TABLE;");
            else
                tableString = tableString.concat(";TABLE;");

            GameModel gameModel = table.getModel(GameModel.class);

            UUID uuid = gameModel.getUUID();
            String name = gameModel.getName();
            String maxPlayers = String.valueOf(gameModel.getMaxPlayers());
            String currentPlayers = String.valueOf(gameModel.getPlayers().stream().filter(p -> !p.isBot()).count());

            if (name == null)
                name = "";

            tableString = tableString.concat(String.format("%s;%s;%s;%s", uuid.toString(), name, currentPlayers, maxPlayers));

            tableNumber++;
        }
//		System.out.println("Server tableString is: " + tableString
//				+ "number of tables: " + activeTables.size());
        return tableString;
    }

}
