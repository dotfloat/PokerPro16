package org.gruppe2.game.session;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Base64;

import org.gruppe2.game.Player;
import org.gruppe2.game.controller.NetworkClientController;
import org.gruppe2.game.model.*;
import org.gruppe2.network.NetworkIO;

public class ClientSession extends Session {

    public ClientSession(String ip) {
        connect(ip);

        addModel(new ChatModel());
        addModel(new StatisticsModel());
    }
    
    public ClientSession(NetworkIO connection) {
    	try {
			waitForSync(connection);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        addModel(new ChatModel());
        addModel(new StatisticsModel());
    }

    @Override
    public void init() {
        addController(NetworkClientController.class);
    }

    @Override
    public void update() {

    }

    private void connect(String ip) {
        try {
            SocketChannel channel = SocketChannel.open(new InetSocketAddress(ip, 8888));
            NetworkIO connection = new NetworkIO(channel);
            channel.configureBlocking(false);
            
            waitForSync(connection);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private void waitForSync(NetworkIO connection) throws IOException, ClassNotFoundException {
        connection.setInputFormat(NetworkIO.Format.OBJECT);
        connection.setOutputFormat(NetworkIO.Format.STRING);

        int numSyncs = 0;
        while (numSyncs < 2) {
            Object object = connection.readObject();

            if (object == null)
                continue;

            if (object instanceof GameModel || object instanceof RoundModel) {
                addModel(object);
                numSyncs++;
            }
        }

        addModel(new NetworkClientModel(connection));

        GameModel gameModel = (GameModel) getModel(GameModel.class);

        for (Player p : gameModel.getPlayers()) {
            System.out.printf("Player: %s (%d)\n", p.getName(), p.getTablePosition());
        }
    }

    private Object deserializeModel(String base64Encoded) throws IOException, ClassNotFoundException {
        byte[] bytes = Base64.getDecoder().decode(base64Encoded);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        Object object = objectInputStream.readObject();

        return object;
    }
}
