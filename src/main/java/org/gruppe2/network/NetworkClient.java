package org.gruppe2.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.gruppe2.game.Action;
import org.gruppe2.game.Player;
import org.gruppe2.game.event.ChatEvent;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.event.QuitEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Query;
import org.gruppe2.game.session.SessionContext;
import org.gruppe2.ui.javafx.InGame;


/**
 * This class will be divided in netclient and clientSession I guess
 * @author htj063
 *
 */
public class NetworkClient implements Runnable {

	private int port = 8888;
	private String host = "localhost";
	boolean firstMessage = true;
	boolean inGame = false;
	boolean notifiedGameStart = false;
	public static boolean clientPressedStart = false;
	String initialMessage = "master";
    String secondMessage = "ok";
    private static String joinMessage = null;
	private boolean lobbyChoosing = false;
	public PrintWriter outPrinter = null;
	public int playerNumber = 1;
	public static boolean onlineGame = false;
	private static SessionContext context = null;
	ArrayList<Player> players = new ArrayList<Player>();
	
	@Helper
    private GameHelper gameHelper;
    @Helper
    private RoundHelper roundHelper;

    
	@Override
	public void run() {
		onlineGame = true;
		try (Socket socket = new Socket("129.177.121.72", port);
				PrintWriter out = new PrintWriter(socket.getOutputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				
				
				BufferedReader stdIn = new BufferedReader(
						new InputStreamReader(System.in));) {
			setOutSocket(out);
			
            while (true) {
            	if(!inGame)
					onServerConnect(out,in);
				else
            		onGameStart(out,in);
            }
		}
        catch(ConnectException e){
			System.out.println("Cound not find an online server ");
		}
		 catch (UnknownHostException h) {
			h.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * Before you are in a game, you must choose what to do, create table, or join a specific table
	 * @param out
	 * @param in
	 * @throws IOException
	 * @throws InterruptedException 
	 */
    public void onServerConnect(PrintWriter out, BufferedReader in) throws IOException, InterruptedException {
    	
    	
    	if (firstMessage) {
            System.out.println("Client: " + initialMessage);
            out.println(initialMessage + "\n");
            out.flush();
            firstMessage = false;
        }   	
    	sleepNowDearThread();
		
    	if(lobbyChoosing && joinMessage != null){
    		
            System.out.println("Client: " + joinMessage);
            out.println(joinMessage + "\n");
            out.flush();
            lobbyChoosing = false;
    	}
    	else if(!lobbyChoosing){
	    	String fromServer = in.readLine();
	    	System.out.println("Recieved from server:" +fromServer);
	        
	        if(fromServer.equals("yes")) {
	            System.out.println("Client: " + secondMessage);
	            out.println(secondMessage + "\n");
	            out.flush();
	        }
	        else if(fromServer.contains("table")){
//	            String[] s = fromServer.split(";");
	            System.out.println("Got table from server: "+ fromServer);
	            showTablesInLobby(fromServer);
	            lobbyChoosing = true;
	            
	        }
	        else if(fromServer.contains("ok") && fromServer.contains("join")) {
	            String[] s = fromServer.split(";");
	            join(Integer.parseInt(s[2]));
	            
        	}
        }
    }
    @Handler
    private void disconnectMe(QuitEvent quitEvent){
    	outPrinter.print(playerNumber+";bye");
    	System.out.println("disconnecting me");
    } 
    
    private void showTablesInLobby(String fromServer) { // Change to message when ready
    	if(NetworkTester.lobby != null)
    		NetworkTester.lobby.updateTables(fromServer);
	}
	public void onGameStart(PrintWriter out, BufferedReader in) throws InterruptedException, IOException {
		
    	if(!notifiedGameStart){
    		System.out.println("game waiting to start,  add players etc..");
    		if(clientPressedStart){
	    		out.print("start");
	    		notifiedGameStart = true;
    		}
    		sleepNowDearThread();
    	}
    	else{
    		sleepNowDearThread();
    		
    		setContext();
    		String fromServer = in.readLine();
    		String[] s = fromServer.split(";");
    		if(s.length == 1){
    			
    		}
    		else if(s.length == 2){
    			
    		}
    		else if(s.length == 3){
    			String playerNumber = s[0];
    			String event = s[1];
    			String message = s[2];
    			if(event.equals("chat")){
    				setChat(playerNumber,message);
    			}
    			if(event.equals("move")){
    				sendMoveMessageToGui(playerNumber,message);
    			}
    		}
    		
    		//TODO create server setup, so that when all players are ready, a signal is given to start game for client.
    	}	
    }
	private void setContext() {
		if(context == null){
			context=InGame.getContext();
			context.setAnnotated(this);
		}
		
		
	}
	public static void setJoinMessage(String messageFromGUI){
		joinMessage = messageFromGUI;
		System.out.println("message set to: "+messageFromGUI);
	}

    public void join(int table) {
    	System.out.println("spillet er klart!");
    	inGame = true;
    }
    private void sleepNowDearThread() throws InterruptedException{	
    	Thread.sleep(100);
    }
    public void setOutSocket(PrintWriter out){
    	this.outPrinter = out;
    }
    
    public void setChat(String playerNumber,String message){
    	System.out.println("client recieved chat");
    	context.message("chat", message, InGame.getPlayerUUID());
    }
    public void sendChat(String message){
    	System.out.println("sending chat");
    	outPrinter.println(message);
    	outPrinter.flush();
    }
    private void sendMoveMessageToGui(String playerID, String message) {
    	
		int playerNumber = Integer.valueOf(playerID);
//		Player player = players.get(playerNumber);
		
		if (message.contains("raise")) {
			int betValue = Integer.valueOf(message.substring(6));
			System.out.println("Player: " + playerID + " raise" + betValue);
			PlayerActionQuery playerActionQuery = new PlayerActionQuery(null, null);
			Query<Action> actionQuery = playerActionQuery.getPlayer().getAction();
			actionQuery.set(new Action.Raise(betValue));
			context.message("sendMessageToClients", "join","1;move;raise "+betValue);//for each player(send 1;move;raise value)

		}
		if (message.contains("call")) {	
			System.out.println("Player: " + playerID + " call");
			context.message("sendMessageToClients", "join","1;move;call");
			//guiClient.setAction(new Action.Call());
		}
		if (message.contains("check")) {
			System.out.println("Player: " + playerID + " check");
			context.message("sendMessageToClients", "join","1;move;check");
			//guiClient.setAction(new Action.Check());
		}
		if (message.contains("fold")) {
			System.out.println("Player: " + playerID + " folded");
			context.message("sendMessageToClients", "join","1;move;fold");
			//guiClient.setAction(new Action.Fold());
		}
	}
    @Handler
    public void chatHandler(ChatEvent chatEvent){
    	Player player = gameHelper.findPlayerByUUID(chatEvent.getPlayerUUID());
    	
    	sendChat("1;chat;"+chatEvent.getMessage());
    }
}
