package org.gruppe2.ui.console;

import org.gruppe2.game.old.*;

import java.util.List;
import java.util.Scanner;

public class ConsoleClient extends GameClient {
    Scanner in = new Scanner(System.in);

    public ConsoleClient() {
        setName("ConsoleClient");
    }

    public static void launch() {
        new GameBuilder()
                .ai(5)
                .blinds(15, 7)
                .startMoney(1000)
                .mainClient(new ConsoleClient())
                .build()
                .mainLoop();
    }

    @Override
    public void onRoundStart() {
        System.out.println("A new round has started");
    }

    @Override
    public void onPlayerVictory(Player player) {
        System.out.println(player.getName() + " has won \n");
    }

    @Override
    public void onPlayerAction(Player player, Action action) {
        System.out.printf("  %s (%d : %d) ", player.getName(), player.getBank(), player.getBet());

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

    @Override
    public Action onTurn(Player player) {
        System.out.println("Highest bet: " + getSession().getHighestBet());
        System.out.println("Table pot: " + getSession().getTable().getPot());
        System.out.printf("Your cards: %s %s \n", player.getCard1(), player.getCard2());
        System.out.printf("Your chips: %d \n", player.getBank());
        System.out.printf("Current bet: %d \n", player.getBet());
        System.out.println("> Your turn you can: ");
        System.out.println(getSession().getPlayerOptions(player));

        Scanner ls = new Scanner(in.nextLine());
        String cmd = ls.next().toLowerCase();

        switch (cmd) {
            case "fold":
                return new Action.Fold();

            case "check":
                return new Action.Check();

            case "call":
                return new Action.Call();

            case "raise":
                return new Action.Raise(ls.nextInt());

            default:
                System.out.println("Unknown command");
                return null;
        }
    }

    @Override
    public void onCommunalCards(List<Card> cards) {
        if (cards == null)
            return;

        System.out.print("Communal cards: ");

        for (Card card : cards) {
            System.out.printf("%s ", card.toString());
        }

        System.out.println();
    }
}
