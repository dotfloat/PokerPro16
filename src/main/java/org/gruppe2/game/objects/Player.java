package org.gruppe2.game.objects;

import org.gruppe2.ui.javafx.PokerApplication;

/**
 * Created by kjors on 09.04.2016.
 */
public class Player {

    String name; String getName() {return name;}

    // create Position-objects later
    int x; int getX() {return x;}
    int y; int getY() {return y;}

    // counting clockwise around the table
    int playerNumber;

    void Player(String name, int playerNumber, PokerApplication game) {

        this.name = name;
        this.playerNumber = playerNumber;

        /**
         * placementcalculation for round table
         * angle = (360 / numOfPlayers) * playerNumber
         * radius = tableHeight / 2
         * x = x(origo) + (radius * cos(angle))
         * y = y(origo) + (radius * sin(angle))
         *
         * for eliptic table
         *
          */
        this.x = game.getWidth();
    }
}
