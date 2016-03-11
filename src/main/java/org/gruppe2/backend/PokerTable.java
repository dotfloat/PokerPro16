package org.gruppe2.backend;

import java.util.ArrayList;

/**
 * Class for a Poker Table
 * Created by cemsepetcioglu on 10.03.16.
 */
public class PokerTable {

    Deck deck;
    public ArrayList<Player> players = new ArrayList<>();
    public ArrayList<Card> communityCards = new ArrayList<>(5);
    public int pot;


    /**
     * Makes a poker table
     * @param deck Deck
     * @param pot Pot on the table
     */
    public PokerTable(Deck deck, int pot) {
        this.deck = deck;
        this.pot = pot;
    }

    /**
     * Draws 5 community cards to the table
     */
    public void drawCommunityCards() {
        communityCards = deck.drawCards(5);
    }

    /**
     * Adding a Player
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * @return a list of all community cards
     */
    public ArrayList<Card> getCardOnTable() {
        return communityCards;
    }

    /**
     * @return a list of all players on the table
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Adding pot to table
     * @param p pot value
     */
    public void addToPot(int p) {
        pot = pot + p;
    }

    /**
     * @return pot on the table
     */
    public int returnPot() {
        return pot;
    }

}
