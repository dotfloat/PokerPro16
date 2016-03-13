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
    public void onOtherPlayerAction(Player player, Action action) {
        System.out.printf("%s (%d) ", player.getName(), player.getChips());

        switch (action.getType()) {
            case FOLD:
                System.out.println("folded.");
                break;

            case ADD_CHIPS:
                System.out.println("");
                break;
        }
    }

    @Override
    public Action onTurn() {
        System.out.print("Your turn (fold, check, raise): ");

        Scanner ls = new Scanner(in.nextLine());
        String cmd = ls.next().toLowerCase();

        switch(cmd) {
            case "fold":
                return new Action(Action.Type.FOLD);

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
        session.addPlayer("AIClient", new AIClient(session));
        session.mainLoop();
    }
}
