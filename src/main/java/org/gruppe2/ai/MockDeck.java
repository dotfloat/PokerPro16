package org.gruppe2.ai;

import java.util.ArrayList;
import java.util.List;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Deck;

public class MockDeck extends Deck{
	public void removeCards(List<Card> cardsToRemove){
		this.cards.remove(cardsToRemove);
	}
	
	public ArrayList<Card> getCards(){
		return this.cards;
	}
}
