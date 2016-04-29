package org.gruppe2.game.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.UUID;

import org.gruppe2.game.event.ChatEvent;
import org.gruppe2.game.event.Event;
import org.gruppe2.game.session.Message;
import org.gruppe2.network.ProtocolReader;

public class NetworkClientController extends AbstractController {
    private SocketChannel socket = null;

    @Override
    public void update() {
        if (socket != null && socket.isConnected()) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.flip();
            buffer.clear();
            int bytesRead = 0;

            try {
                if ((bytesRead = socket.read(buffer)) == 0)
                    return;
            } catch (IOException e) {
                onDisconnect(e);
            }
            
            String serverMSG = new String(buffer.array());
            Event event = ProtocolReader.parseEvent(ProtocolReader.reader(serverMSG));
            System.out.println(event.getClass());
            System.out.println(serverMSG);
            addEvent(new ChatEvent(serverMSG, UUID.randomUUID()));
        }
    }

    @Message
    public void connect() {
        try {
            socket = SocketChannel.open();
            socket.connect(new InetSocketAddress(8888));
            socket.configureBlocking(false);
            
            sendHandShake();
            
        } catch (IOException e) {
            onDisconnect(e);
        }

        System.out.println("Connected");
    }
    private void sendHandShake() {
		boolean handShakeFinished = true; //Lets pretend handshake and lobby choosing is finished
		
    	if(handShakeFinished){
    		sendPlayerInfo();
    	}
	}
    
    private void sendPlayerInfo() {
		// TODO Auto-generated method stub
	}

	private void sendToServer(String mesg){
    	
    	ByteBuffer buf = ByteBuffer.allocate(mesg.length());
        buf.clear();
        buf.put(mesg.getBytes());
        buf.flip();

        try {
        	socket.write(buf);
        } catch (IOException e) {
            onDisconnect(e);
        }
    }

	private void onDisconnect(IOException e) {
		e.printStackTrace();
		if(socket != null)
			socket = null;
		System.out.println("Connection failed");
        getContext().quit();
	}

	/**
     * Used to receive objects over socket channel
     */
    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
            try(ObjectInputStream o = new ObjectInputStream(b)){
                return o.readObject();
            }
        }
    }
}
