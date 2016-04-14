package org.gruppe2.ui.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kjors on 09.04.2016.
 */
public class Table {

    static int numberOfPlayers; // get from gameSession ?
    static int radius = 300; // get from settings ?

    ArrayList<LinkedList<Player>> table; // full table

    static LinkedList<Player> left;
    static LinkedList<Player> middle;
    static LinkedList<Player> right;


    public Table() {
        table = new ArrayList<>();
        left = new LinkedList<>();
        middle = new LinkedList<>();
        right = new LinkedList<>();

        table.add(left);
        table.add(middle);
        table.add(right);
    }


    /**
     * placementcalculation for round table
     *
     * angle = (360 / numOfPlayers) * playerNumber
     * radius = tableHeight / 2
     * x = x(origo) + (radius * cos(angle))
     * y = y(origo) + (radius * sin(angle))
     *
     *
     * for eliptic table
     *
     * semiCircle + sqare + semiCircle
     * table square side = tableHeight * tableHeight
     * semiCircle circumference = (pi * tableHeight) / 2
     * (semiCircle * 2 = 3 times bigger than table square side)
     *
     *
     */

    public static Position getPosition(Player player) {
        int x = 0;
        int y = 0;

        int currentIndex;

        // player is on long side...
        // move Y to top of screen / table
        // spread out X equidistant along table long-side
        if ((currentIndex = middle.indexOf(player)) > 0) {

            y = radius;
            x = (radius / numberOfPlayers) * currentIndex;
        }

        /**
         * TODO: fix casting, may be slow ?
         */

        // player is on left side...
        // using polar coordinate calculation (+180 degrees gives left circle)
        else if ((currentIndex = left.indexOf(player)) > 0) {

            double angle = 180 + (90 / left.size() - currentIndex);
            y = ((int) ((radius) + ((radius) * (Math.sin(angle)))));
            x = ((int) ((radius) + ((radius) * (Math.cos(angle)))));
        }

        // player is on right side
        else if ((currentIndex = right.indexOf(player)) > 0) {

            double angle = (90 / left.size() + currentIndex);
            y = ((int) ((radius) + ((radius) * (Math.sin(angle)))));
            x = ((int) ((radius) + ((radius) * (Math.cos(angle)))));
        }

        // no player???
        else return null;

        // return position of player
        return new Position( x, y );
    }

    /**
     * Generates symmetric table placement
     * @param player player object to add
     */
    public void addPlayer(Player player) {

        // if 3, 5, 8 or 11 players are sitting down, we need
        // to move players to the sides...
        boolean reflowTable = Arrays.asList(new int[]{3, 5, 8, 11}).contains(numberOfPlayers);

        if (reflowTable) {
            middle.add(player);
            left.add(middle.removeFirst());
            right.add(middle.removeLast());
        }

        // otherwise just place on the long-side of table
        else {
            middle.add(player);
        }
    }

 }
