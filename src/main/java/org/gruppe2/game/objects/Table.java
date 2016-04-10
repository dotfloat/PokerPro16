package org.gruppe2.game.objects;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by kjors on 09.04.2016.
 */
public class Table {

    int numberOfPlayers;

    ArrayList<LinkedList<Player>> table; // left, middle and right

    LinkedList<Player> left;
    LinkedList<Player> middle;
    LinkedList<Player> right;


    public Table() {
        table = new ArrayList<>();
        left = new LinkedList<>();
        middle = new LinkedList<>();
        right = new LinkedList<>();

        table.add(left);
        table.add(middle);
        table.add(right);


    }

    public void addPlayer(Player player) {
        switch (numberOfPlayers) {
            case 1:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 10: middle.add(player);
                break;

            case 3:
            case 5:
            case 8:
            case 11: {
                middle. add(player);
                left.add(middle.removeFirst());
                right.add(middle.removeLast());
                break;
            }

            default: break;
        }
    }
}
