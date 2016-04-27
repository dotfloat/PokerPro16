package org.gruppe2.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

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
	
	public static ArrayList<Thread> threads = new ArrayList<Thread>();
	public static ArrayList<NetworkServerGameSession> gamesOnServer = new ArrayList<NetworkServerGameSession>();
	protected Thread runningThread = null;
	

	public NetworkServer(int port) {
		this.port = port;
		getIpofLocalComputerTest();
		getIpOfThisServerReal();
	}

	

	@Override
	public void run() {
		synchronized (this) {
			this.runningThread = Thread.currentThread();
		}

		openServer();
		while (!notRunning) {
			try {
				clientSocket = this.serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Thread t = new Thread(new NetworkServerWorker(clientSocket,
					playerID));
			t.start();
			threads.add(t);
			System.out.println("Connection received from: " + t.getName());
			for (Thread thr : threads)
				System.out.println(thr.getId() + " "
						+ thr.getState().toString());
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

	public static void removeThread(Thread t) {
		threads.remove(t);
	}

	
	private void getIpOfThisServerReal() {
		try {
			System.out.println("Host addr: " + InetAddress.getLocalHost().getHostAddress());
			Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
			 for (; n.hasMoreElements();)
		        {
		                NetworkInterface e = n.nextElement();
		                System.out.println("Interface: " + e.getName());
		                Enumeration<InetAddress> a = e.getInetAddresses();
		                for (; a.hasMoreElements();)
		                {
		                        InetAddress addr = a.nextElement();
		                        System.out.println("  " + addr.getHostAddress());
		                }
		        }
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}  // often returns "127.0.0.1"
		catch (SocketException e1) {
			e1.printStackTrace();
		}
}
	

	private void getIpofLocalComputerTest() {
		InetAddress ip;
		  try {
	
			ip = InetAddress.getLocalHost();
			System.out.println("Current IP address : " + ip.getHostAddress());
	
		  } catch (UnknownHostException e) {
			e.printStackTrace();
		  }
		
	}
	/**
	 * This might be used when game is finished to start server ?
	 * @param args
	 */
	public static void main(String[] args) {

		NetworkServer server = new NetworkServer(8888);
		server.run();
	}
}
