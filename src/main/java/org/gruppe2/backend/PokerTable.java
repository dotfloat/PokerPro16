package org.gruppe2.backend;

import java.util.ArrayList;

/**
 * Created by cemsepetcioglu on 10.03.16.
 */
public class PokerTable {

    Deck deck;
    public ArrayList<Player> players = new ArrayList<>();
    public ArrayList<Card> communityCards = new ArrayList<>(5);
    public int pot;


    public PokerTable(Deck deck, ArrayList<Player> players, int pot) {
        this.deck = deck;
        this.players = players;
        this.pot = pot;
    }

    public void drawCommunityCards() {
        communityCards = deck.drawCards(5);
    }

    public ArrayList<Card> getCardOnTable() {
        return communityCards;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addToPot(int p) {
        pot = pot + p;
    }

    public int returnPot() {
        return pot;
    }

}
