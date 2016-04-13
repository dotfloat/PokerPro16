package org.gruppe2.ui.console;

import org.gruppe2.Main;
import org.gruppe2.game.GameBuilder;
import org.gruppe2.game.Handler;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.event.PlayerLeaveEvent;
import org.gruppe2.game.event.RoundStartEvent;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.old.Action;
import org.gruppe2.game.session.SessionContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.UUID;

public class ConsoleApplication implements Runnable {
    private SessionContext context;

    public void init() {
        GameBuilder gameBuilder = new GameBuilder();

        System.out.println("Welcome to PokerPro16 Console Edition");

        if (!Main.isAutostart()) {
            throw new NotImplementedException();
        }

        context = gameBuilder.start();
        context.registerAnnotatedHandlers(this);
        context.waitReady();
        context.message("addPlayer", UUID.randomUUID(), "Zohar", "zohar");
    }

    @Override
    public void run() {
        init();

        while (true) {
            context.getEventQueue().process();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Handler
    void onAction(PlayerActionQuery query) {
        System.out.println("Action");

        query.set(new Action.Fold());
    }

    @Handler
    void onPlayerJoin(PlayerJoinEvent event) {
        System.out.println(event.getPlayerModel().getName() + " has connected");
    }

    @Handler
    void onPlayerLeave(PlayerLeaveEvent event) {
        System.out.println(event.getPlayerModel().getName() + " has disconnected");
    }

    @Handler
    void onRoundStart(RoundStartEvent event) {
        System.out.println("A new round has started");
    }

    public SessionContext getContext() {
        return context;
    }
}
