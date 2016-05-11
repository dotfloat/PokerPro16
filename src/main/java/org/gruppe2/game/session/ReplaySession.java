package org.gruppe2.game.session;

import org.gruppe2.Resources;
import org.gruppe2.game.Player;
import org.gruppe2.game.controller.ClientRoundController;
import org.gruppe2.game.controller.NetworkClientController;
import org.gruppe2.game.controller.ReplayController;
import org.gruppe2.game.controller.StatisticsController;
import org.gruppe2.game.model.*;
import org.gruppe2.network.NetworkIO;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Base64;

public class ReplaySession extends Session {

    public ReplaySession() {
        try {
            Object object;

            InputStream stream = new FileInputStream(Resources.getUserDir() + "demo.pp16");
            addModel(new ReplayModel(stream));

            if (!((object = readObject(stream)) instanceof GameModel))
                throw new RuntimeException();

            addModel((GameModel) object);

            if (!((object = readObject(stream)) instanceof  RoundModel))
                throw new RuntimeException();

            addModel((RoundModel) object);

            addModel(new StatisticsModel());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        addController(ReplayController.class);
        addController(ClientRoundController.class);
        addController(StatisticsController.class);
    }

    @Override
    public void update() {

    }

    public static Object readObject(InputStream stream) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(stream);

            return objectInputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
