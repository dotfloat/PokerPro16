package org.gruppe2.ui.javafx.ingame;

import javafx.application.Platform;
import org.gruppe2.Main;
import org.gruppe2.game.GameBuilder;
import org.gruppe2.game.event.QuitEvent;
import org.gruppe2.game.session.Query;
import org.gruppe2.game.session.SessionContext;
import org.w3c.dom.Node;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class Game {
    private final static Game instance = new Game();

    private UUID playerUUID;
    private SessionContext context = null;
    private Timer sessionTimer = new Timer();

    private Game() {
        try {
            playerUUID = UUID.fromString(Main.getProperty("playerUUID"));
        } catch (Exception e) {
            playerUUID = UUID.randomUUID();
            Main.setProperty("playerUUID", playerUUID.toString());
        }
    }

    public static UUID getPlayerUUID() {
        return instance.playerUUID;
    }

    public static SessionContext getContext() {
        return instance.context;
    }

    public static void setContext(SessionContext context) {
        quit();

        instance.context = context;
        instance.context.getEventQueue().registerHandler(QuitEvent.class, event -> quit());

        startTimer();
    }

    public static void autostart() {
        setContext(new GameBuilder().start());
    }

    public static Query<Boolean> message(String name, Object... args) {
        return getContext().message(name, args);
    }

    public static void setAnnotated(Object object) {
        getContext().setAnnotated(object);
    }

    private static void startTimer() {
        instance.sessionTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> instance.context.getEventQueue().process());
            }
        }, 0, 50);
    }

    private static void quit() {
        if (instance.context == null)
            return;

        instance.context.quit();
        instance.sessionTimer.cancel();
        instance.sessionTimer = null;

        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }

        // Process the queue one last time
        instance.context.getEventQueue().process();
        instance.context = null;
    }
}
