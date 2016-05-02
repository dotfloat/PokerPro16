package org.gruppe2.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.UUID;

import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.session.HostSession;

public class MasterServer {
	ArrayList<HostSession> activeTables = new ArrayList<HostSession>();
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
                		System.out.println("Client said hello to me");
                		clients.get(i).sendMessage("HELLO;MASTER\r\n");
                		clients.get(i).sendMessage(createTableString()+"\r\n");
                		break;
                	case "JOIN TABLE":
                		
                		if(canJoinTable(args[1])){
                			clients.get(i).sendMessage("JOINED;"+args[1]);
                			connectClientToTable();
                		}
                		else{
                			clients.get(i).sendMessage("NO");
                		}
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
			int maxPlayers = activeTables.get(tableNumber).getContext().getModel(GameModel.class).getMaxPlayers();
			int currentPlayers = activeTables.get(tableNumber).getContext().getModel(GameModel.class).getPlayers().size();
			if(currentPlayers < maxPlayers){
				return true;
			}
		}
		return false;
	}
	
	private int getTableNumberFromUUID(String tableUUID) {
		int index = 0;
		for(HostSession table : activeTables){
			UUID uuid = table.getContext().getModel(GameModel.class).getUUID();
			if(uuid.toString().equals(tableUUID)){
				return index;
			}
			index++;
		}
		return -1;
	}

	private void connectClientToTable() {
		// TODO Auto-generated method stub
		
	}


	
	public String createTableString(){
		String tableString = "";
		int tableNumber = 0;
		for(HostSession table : activeTables){
			if(tableNumber == 0)
				tableString.concat("TABLE;");
			else
				tableString.concat(";TABLE;");
			
			UUID uuid = table.getContext().getModel(GameModel.class).getUUID();
			String maxPlayers =  String.valueOf(table.getContext().getModel(GameModel.class).getMaxPlayers());
			String currentPlayers =  String.valueOf(table.getContext().getModel(GameModel.class).getPlayers().size());
			tableString.concat(uuid.toString()+";"+currentPlayers+";"+maxPlayers);
			
			tableNumber++;
		}
		
		return tableString;
	}
	
	public void addTable(HostSession session){
		activeTables.add(session);
	}
	public ArrayList<HostSession> getTables(){
		return activeTables;
	}
}
