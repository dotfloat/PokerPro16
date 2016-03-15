package org.gruppe2.console;

import org.gruppe2.ai.AIClient;
import org.gruppe2.backend.*;

import java.util.List;
import java.util.Scanner;

public class ConsoleClient extends GameClient {
    Scanner in = new Scanner(System.in);

    public ConsoleClient(GameSession session) {
        super(session);
    }

    @Override
    public void onRoundStart() {
        System.out.println("A new round has started");
    }

    @Override
    public void onPlayerVictory(Player player) {
        System.out.println(player.getName() + " has won");
    }

    @Override
    public void onOtherPlayerAction(Player player, Action action) {
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
    public Action onTurn() {
        System.out.print("> Your turn (fold, check, raise): ");

        Scanner ls = new Scanner(in.nextLine());
        String cmd = ls.next().toLowerCase();

        switch(cmd) {
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
        System.out.print("Communal cards: ");

        for (Card card : cards) {
            System.out.printf("%s ", card.toString());
        }

        System.out.println();
    }

    public static void main(String[] args) {
        GameSession session = new GameSession();
        session.addPlayer("ConsoleClient", new ConsoleClient(session));
        session.addPlayer("Anne", new AIClient(session));
        session.addPlayer("Bob", new AIClient(session));
        session.addPlayer("Chuck", new AIClient(session));
        session.addPlayer("Dennis", new AIClient(session));
        session.addPlayer("Emma", new AIClient(session));
        session.mainLoop();
    }
}