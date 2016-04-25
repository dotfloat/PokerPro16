package org.gruppe2.ui.console;

import org.gruppe2.Main;
import org.gruppe2.game.GameBuilder;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.event.*;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.Action;
import org.gruppe2.game.session.Query;
import org.gruppe2.game.session.SessionContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class ConsoleApplication implements Runnable {
    private SessionContext context;
    private UUID playerUUID;
    private Query<Action> action;

    private void init() {
        GameBuilder gameBuilder = new GameBuilder();

        System.out.println("Welcome to PokerPro16 Console Edition");

        if (!Main.isAutostart()) {
            throw new NotImplementedException();
        }

        playerUUID = UUID.randomUUID();
        action = new Query<>();

        context = gameBuilder.start();
        context.setAnnotated(this);
        context.waitReady();

        if (context.message("addPlayer", playerUUID, "Zohar", "zohar", action).get()) {
            System.out.println("Successfully added the player");
        } else {
            System.out.println("Unsuccessfully");
        }
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
    public void onAction(PlayerActionQuery query) {
        if (!query.getPlayerModel().getUUID().equals(playerUUID))
            return; // Query isn't for us :(

//        System.out.println("Highest bet: " + getSession().getHighestBet());
//        System.out.println("Table pot: " + getSession().getTable().getPot());
//        System.out.printf("Your cards: %s %s \n", player.getCard1(), player.getCard2());
//        System.out.printf("Your chips: %d \n", player.getBank());
//        System.out.printf("Current bet: %d \n", player.getBet());
        System.out.println("> Your turn, you can: ");
        System.out.println(query.getPlayerModel().getOptions());

        System.out.println("> ");

        Scanner in = new Scanner(System.in);
        Scanner ls = new Scanner(in.nextLine());
        String cmd = ls.next().toLowerCase();

        switch (cmd) {
            case "fold":
                query.getPlayerModel().getAction().set(new Action.Fold());
                break;

            case "check":
                query.getPlayerModel().getAction().set(new Action.Check());
                break;

            case "call":
                query.getPlayerModel().getAction().set(new Action.Call());
                break;

            case "raise":
                query.getPlayerModel().getAction().set(new Action.Raise(ls.nextInt()));
                break;

            default:
                System.out.println("Unknown command");
                break;
        }
    }

    @Handler
    public void onPostAction(PlayerPostActionEvent event) {
        PlayerModel player = event.getPlayerModel();
        Action action = event.getAction();

        System.out.printf("  %s (%d : %d) ", player.getName(), player.getBank(), 0 /* getBet */);

        if (action instanceof Action.Fold) {
            System.out.println("folded");
        } else if (action instanceof Action.Call) {
            System.out.println("called");
        } else if (action instanceof Action.Check) {
            System.out.println("checked");
        } else if (action instanceof Action.Raise) {
            System.out.println("raised by " + ((Action.Raise) action).getAmount());
        } else {
            System.out.println("!!! " + action);
        }
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

    @Handler
    void onPlayerWon(PlayerWonEvent event) {
        System.out.println("Player " + event.getPlayerModel().getName() + " has won!");
    }

    public SessionContext getContext() {
        return context;
    }
}
