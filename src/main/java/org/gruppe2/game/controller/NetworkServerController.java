package org.gruppe2.game.controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import org.gruppe2.game.session.Message;


public class NetworkServerController extends AbstractController{
	ServerSocketChannel serverSocket;
	ArrayList<SocketChannel> clients = new ArrayList<SocketChannel>();
	@Override
	public void update(){
		if(serverSocket != null){
			try {
				SocketChannel client = serverSocket.accept();
				if(client != null){
					client.configureBlocking(false);
					clients.add(client);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		for(SocketChannel client : clients){
			ByteBuffer buff = ByteBuffer.allocate(100);
			buff.clear();
			buff.put("hello World!!<3".getBytes());
			buff.flip();
			try {
				client.write(buff);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	@Message
	public void listen(){
		try {
			serverSocket = ServerSocketChannel.open();
			serverSocket.socket().bind(new InetSocketAddress(8888));
			serverSocket.configureBlocking(false);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}


