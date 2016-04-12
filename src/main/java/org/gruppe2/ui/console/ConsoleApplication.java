package org.gruppe2.ui.console;

import org.gruppe2.Main;
import org.gruppe2.game.GameBuilder;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.old.Action;
import org.gruppe2.game.session.SessionContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.UUID;

public class ConsoleApplication implements Runnable {
    private SessionContext sessionContext;
    private PlayerModel playerModel = new PlayerModel(UUID.randomUUID(), "Thomas Lund Mathisen", "thomas");

    @Override
    public void run() {
        GameBuilder gameBuilder = new GameBuilder();

        System.out.println("Welcome to PokerPro16 Console Edition");

        if (Main.isAutostart()) {
            gameBuilder.maxPlayers(10);
        } else {
            throw new NotImplementedException();
        }

        sessionContext = gameBuilder.start();

        sessionContext.getGameView().onPlayerJoin(System.out::println);

        System.out.println();

        sessionContext.getEventQueue().registerHandler(PlayerActionQuery.class, playerModel, this::action);
        sessionContext.addPlayer(playerModel);

        while (true) {
            sessionContext.getEventQueue().process();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void action(PlayerActionQuery query) {
        System.out.println("Action");

        query.set(new Action.Fold());
    }

    public SessionContext getSessionContext() {
        return sessionContext;
    }
}
