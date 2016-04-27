package org.gruppe2.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.gruppe2.game.session.Handler;



public class NetworkClient implements Runnable {

	private int port = 8888;
	private String host = "localhost";
	boolean firstMessage = true;
	boolean inGame = false;
	boolean notifiedGameStart = false;
	String initialMessage = "master";
    String secondMessage = "ok";
    private static String joinMessage = null;
	private boolean lobbyChoosing = false;
	public PrintWriter outPrinter = null;
	public int playerNumber = 1;
    
	@Override
	public void run() {

		try (Socket socket = new Socket("129.177.121.72", port);
				PrintWriter out = new PrintWriter(socket.getOutputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				
				
				BufferedReader stdIn = new BufferedReader(
						new InputStreamReader(System.in));) {
				setInSocket(out);
			
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
    private void disconnectMe(){
    	outPrinter.print(playerNumber+";bye");
    	System.out.println("disconnecting me");
    } 
    
    private void showTablesInLobby(String fromServer) { // Change to message when ready
    	if(NetworkTester.lobby != null)
    		NetworkTester.lobby.updateTables(fromServer);
	}
	public void onGameStart(PrintWriter out, BufferedReader in) throws InterruptedException, IOException {
		
    	if(!notifiedGameStart){
    		System.out.println("client ingame jippi!");
    		notifiedGameStart = true;
    	}
    	else{
    		sleepNowDearThread();
    		System.out.println("game waiting to start,  add players etc..");
    		String fromServer = in.readLine();
    		//TODO create server setup, so that when all players are ready, a signal is given to start game for client.
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
    	if(!notifiedGameStart)
    		System.out.println("choose join or create?");
    	Thread.sleep(100);
    }
    public void setInSocket(PrintWriter out){
    	this.outPrinter = out;
    }
}
