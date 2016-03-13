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

        switch (action.getType()) {
            case FOLD:
                System.out.println("folded.");
                break;

            case ADD_CHIPS:
                System.out.println("");
                break;

            case SMALL_BLIND:
                System.out.println("paid the small blind: " + getSession().getSmallBlindAmount());
                break;

            case BIG_BLIND:
                System.out.println("paid the big blind: " + getSession().getBigBlindAmount());
                break;

            default:
                System.out.println("!!! " + action.getType());
                break;
        }
    }

    @Override
    public Action onTurn() {
        System.out.print("> Your turn (fold, check, raise): ");

        Scanner ls = new Scanner(in.nextLine());
        String cmd = ls.next().toLowerCase();

        switch(cmd) {
            case "fold":
                return new Action(Action.Type.FOLD);

            case "check":
                return new Action(Action.Type.ADD_CHIPS, 0);

            default:
                System.out.println("Unknown command");
                return new Action(Action.Type.DISCONNECT);
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
