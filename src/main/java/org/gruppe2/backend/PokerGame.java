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

    //need pokerTable

    public PokerGame(GUI gui, ArrayList<Player> players, int smallBlind, int bigBlind){
        this.gui = gui;
        this.players = players;
        this.smallBlind = smallBlind;
        this.bigBlind = bigBlind;

        //need poker table and be able give players to board, and board to players
    }

    //todo
    public void addPlayer(Player player){
        players.add(player);
    }

    //todo
    public void createPokerTable(){
    }

    //and many more methods


    public static void main(String[] args) {
        System.out.println("dette blir det beste spillet");
    }
}
