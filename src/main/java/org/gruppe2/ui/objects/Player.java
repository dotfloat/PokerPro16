/**
 * This is tha player model. It will keep all data related
 * to player, and communicate against game-session
 */
package org.gruppe2.ui.objects;

import org.gruppe2.ui.javafx.PokerApplication;

/**
 * Created by kjors on 09.04.2016.
 */
public class Player {

    String name; public String getName() {return name;}

    // temporary points for testing
    // ui stuff, perhaps best in ui-package?
    int angle; // from horizontal straight line, polar coordinate (1,0)

    // create Position-objects later
    int x; int getX() {return x;}
    int y; int getY() {return y;}

    // counting clockwise around the table
    int playerNumber;

    public Player(String name, int playerNumber, PokerApplication game) {

        this.name = name;
        this.playerNumber = playerNumber;

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
         * number of players on square side = floor(numOfPlayer / 5) ?
         *
         *
          */


        // temporary placement algorithm
        switch (game.getNumberOfPlayers()) {
            case 2:
        }
        this.x = game.getWidth();
    }

	public String getStack() {
		return "0";
	}
}
