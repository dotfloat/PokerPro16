package org.gruppe2.frontend;

import java.util.ArrayList;

/**
 * Created by cemsepetcioglu on 10.03.16.
 */
public class PokerTable {

    Deck deck;
    public ArrayList<Player> players = new ArrayList<>();
    public ArrayList<Card> communityCards = new ArrayList<>(5);
    public int pot;
    


    public PokerTable(Deck deck, int pot) {
        this.deck = deck;
        this.pot = pot;
    }

    public void drawCommunityCards() {
        communityCards = deck.drawCards(5);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public ArrayList<Card> getCardOnTable() {
        return communityCards;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    public void  setPlayers(ArrayList<Player> listOfPlayers) {
        players = listOfPlayers;
    }

    public void addToPot(int p) {
        pot = pot + p;
    }

    public int returnPot() {
        return pot;
    }

}
