package org.gruppe2.game.session;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Base64;

import org.gruppe2.game.Player;
import org.gruppe2.game.controller.NetworkClientController;
import org.gruppe2.game.model.ChatModel;
import org.gruppe2.game.model.GameModel;
import org.gruppe2.game.model.NetworkClientModel;
import org.gruppe2.game.model.StatisticsModel;
import org.gruppe2.network.ProtocolConnection;

public class ClientSession extends Session {

    public ClientSession(String ip) {
        connect(ip);

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
            int numSyncs = 0;
            SocketChannel channel = SocketChannel.open(new InetSocketAddress(ip, 8888));
            ProtocolConnection connection = new ProtocolConnection(channel);
            channel.configureBlocking(false);

            while (numSyncs < 2) {
                String[] args = connection.readMessage();

                if (args == null || !args[0].equals("SYNC"))
                    continue;

                Object model = deserializeModel(args[2]);

                addModel(model);

                numSyncs++;
            }

            addModel(new NetworkClientModel(connection));

            GameModel gameModel = (GameModel) getModel(GameModel.class);

            for (Player p : gameModel.getPlayers()) {
                System.out.println("Player: " + p.getName());
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
