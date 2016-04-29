package org.gruppe2.game.controller;

import org.gruppe2.game.event.Event;
import org.gruppe2.game.session.Message;
import org.gruppe2.network.ProtocolReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

public class NetworkClientController extends AbstractController {
    private SocketChannel socket = null;
    private StringBuffer readBuffer = new StringBuffer();

    @Override
    public void update() {
        if (socket != null && socket.isConnected()) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.flip();
            byteBuffer.clear();

            try {
                if (socket.read(byteBuffer) <= 0)
                    return;
            } catch (IOException e) {
                e.printStackTrace();
            }

            readBuffer.append(new String(byteBuffer.array()));

            int indexOfCLRF = readBuffer.indexOf("\r\n");

            if (indexOfCLRF == -1)
                return;

            String serverMessage = readBuffer.substring(0, indexOfCLRF);
            readBuffer.replace(0, indexOfCLRF + 2, "");

            String[] args = ProtocolReader.reader(serverMessage);

            System.out.println("Received: " + Arrays.toString(args));

            if (args[0].equals("SYNC")) {
                try {
                    Object obj = deserialize(Base64.getDecoder().decode(args[2].getBytes()));

                    System.out.println(obj);
                    System.out.println(obj.getClass());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            Event event = ProtocolReader.parseEvent(args);
            if(event != null)
            	addEvent(event);
        }
    }

	@Message
    public void connect() {
        try {
            socket = SocketChannel.open();
            socket.connect(new InetSocketAddress(8888));
            socket.configureBlocking(false);

        } catch (IOException e) {
            onDisconnect(e);
        }

        System.out.println("Connected");
    }

    @Message
    public void chat(String message, UUID playerUUID) {
        sendToServer("SAY:" + message);
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
    	ByteBuffer buf = ByteBuffer.allocate(mesg.length() * 2);
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
