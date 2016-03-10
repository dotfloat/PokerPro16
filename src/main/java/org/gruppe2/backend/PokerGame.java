package org.gruppe2.backend;

import org.gruppe2.frontend.GUI;

import java.util.ArrayList;

/**
 * class to play a pokergame
 */
public class PokerGame{

    GUI gui;
    int startChips;
    int smallBlind;
    int bigBlind;

    private ArrayList<Player> players;
    private PokerTable table;

    
    
    
    public PokerGame(GUI gui, int startChips, int smallBlind, int bigBlind){ //parameterene startchips, smallBlind, og bigBLind er input i GUIen som brukeren skriver inn.
        this.gui = gui;
        this.smallBlind = smallBlind;
        this.bigBlind = bigBlind;
        this.startChips = startChips;

        // table = new PokerTable(new Deck());
    }

    public void addPlayer(String name){
        Player newPlayer = new Player(name, startChips, table);
        players.add(newPlayer);
      //table.addPlayer(newPlayer);
    }


    public ArrayList<Player> getPlayers() {
        return players;
    }


    


}
