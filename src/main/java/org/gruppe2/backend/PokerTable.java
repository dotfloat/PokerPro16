package org.gruppe2.backend;

import java.util.ArrayList;

/**
 * Created by cemsepetcioglu on 10.03.16.
 */
public class PokerTable {

    Deck deck;
    public ArrayList<Player> players = new ArrayList<>();
    public ArrayList<Card> communityCards = new ArrayList<>(5);


    public PokerTable(Deck deck, ArrayList<Player> players) {
        this.deck = deck;
        this.players = players;
    }

    public ArrayList<Card> getCardOnTable() {
        communityCards = deck.drawCards(5);
        return communityCards;
    }

}
