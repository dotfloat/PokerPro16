package org.gruppe2.backend;

import org.gruppe2.frontend.GUI;

import java.util.ArrayList;

/**
 * class to play a pokergame
 */
public class PokerGame{

    GUI gui;
    int smallBlind;
    int bigBlind;

    private ArrayList<Player> players;
    private PokerTable table;


    //need pokerTable CEM PLS 
    
    
    
    public PokerGame(GUI gui){
        this.gui = gui;
        // PokerTable = new PokerTable(new Deck());
        //need poker table and be able give players to board, and board to players
    }

    

    // Skal ikke stå slik, er bare for foreløpig oversikt
    public ArrayList<Player> getPlayers() {
        return players;
    }


    


}
