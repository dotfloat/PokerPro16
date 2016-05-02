package org.gruppe2.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.UUID;

import org.gruppe2.game.GameBuilder;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.session.HostSession;
import org.gruppe2.game.session.SessionContext;

public class MasterServer {
	ArrayList<SessionContext> activeTables = new ArrayList<SessionContext>();
	ServerSocketChannel serverSocket;
	private ArrayList<ProtocolConnection> clients = new ArrayList<>();
	
	public MasterServer() {
		startServer();
		while(true){
			update();
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void startServer(){
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
                	System.out.println("adding client to list");
                    ProtocolConnection connection = new ProtocolConnection(client);
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
                System.out.println("server recieved message");
                switch (args[0]) {
                	case "HELLO":
                		clients.get(i).sendMessage("HELLO;MASTER\r\n");
                		clients.get(i).sendMessage(createTableString()+"\r\n");
                		break;
                	case "JOIN TABLE":
                		
                		if(canJoinTable(args[1])){
                			clients.get(i).sendMessage("JOINED;"+args[1]+"\r\n");
                			connectClientToTable(clients.get(i),args[1]);
                		}
                		else{
                			clients.get(i).sendMessage("NO\r\n");
                		}
                		break;
                	case "CREATE":
                		System.out.println("player pressed create, this is from server");
                		clients.get(i).sendMessage("CREATED\r\n");
                		
                		SessionContext context = new GameBuilder().start();
                		
                		context.waitReady();
                		context.message("addClient", clients.get(i));
                		
                		activeTables.add(context);
                		
                		break;
                		
                		
                    
                }
            } catch (IOException e) {
                clients.remove(i--);
            }
        }
    }
	
	

	private boolean canJoinTable(String tableUUID) {
		
		
		int tableNumber = getTableNumberFromUUID(tableUUID);
		if(tableNumber == -1){
			return false;
		}
		
		if(activeTables.size() >= tableNumber){
			int maxPlayers = activeTables.get(tableNumber).getModel(GameModel.class).getMaxPlayers();
			int currentPlayers = activeTables.get(tableNumber).getModel(GameModel.class).getPlayers().size();
			if(currentPlayers < maxPlayers){
				return true;
			}
		}
		return false;
	}
	
	private int getTableNumberFromUUID(String tableUUID) {
		int index = 0;
		for(SessionContext table : activeTables){
			UUID uuid = table.getModel(GameModel.class).getUUID();
			if(uuid.toString().equals(tableUUID)){
				return index;
			}
			index++;
		}
		return -1;
	}

	private void connectClientToTable(ProtocolConnection client, String tableUUID) {
		int tableNumber = getTableNumberFromUUID(tableUUID);
		
		activeTables.get(tableNumber).message("addClient", client);
	}


	
	public String createTableString(){
		String tableString = "";
		int tableNumber = 0;
		for(SessionContext table : activeTables){
			System.out.println("there should now be at least 1 table!");
			if(tableNumber == 0)
				tableString = tableString.concat("TABLE;");
			else
				tableString = tableString.concat(";TABLE;");
			
			UUID uuid = table.getModel(GameModel.class).getUUID();
			String maxPlayers =  String.valueOf(table.getModel(GameModel.class).getMaxPlayers());
			String currentPlayers =  String.valueOf(table.getModel(GameModel.class).getPlayers().size());
			tableString = tableString.concat(uuid.toString()+";"+currentPlayers+";"+maxPlayers);
			
			tableNumber++;
		}
		System.out.println("Server tableString is: "+tableString+"number of tables: "+activeTables.size());
		return tableString;
	}
	
}
