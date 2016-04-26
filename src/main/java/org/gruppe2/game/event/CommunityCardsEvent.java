package org.gruppe2.game.event;

import org.gruppe2.game.Card;

import java.util.List;

public class CommunityCardsEvent implements Event {
    private List<Card> cards;

    public CommunityCardsEvent(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }
}
