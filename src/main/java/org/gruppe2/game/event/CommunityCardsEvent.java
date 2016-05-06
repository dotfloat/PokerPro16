package org.gruppe2.game.event;

import org.gruppe2.game.Card;

import java.util.List;

public class CommunityCardsEvent implements Event {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2475925120791542036L;
	private List<Card> cards;

    public CommunityCardsEvent(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }
}
