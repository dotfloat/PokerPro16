package org.gruppe2.ai;

import java.util.ArrayList;
import java.util.List;

import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.Deck;
import org.gruppe2.game.old.Player;
import org.gruppe2.game.old.Table;

public class AIHandCalculator {
	
	/**
	 * Simulates x number of rounds with the current AI hand to return how many 
	 * of the rounds the AI will win with current hand
	 * @param table
	 * @param players
	 * @param ai
	 * @return double handStrength
	 */
	public static double getHandStrength(Table table, ArrayList<Player> players, Player player){
		if (table!=null)
			return 0;
		double handStrength=0;
		
		MockDeck d = new MockDeck();
		List<Card> cardsToRemove = table.getCommunityCards();
		cardsToRemove.add(player.getCard1());
		cardsToRemove.add(player.getCard2());
		d.removeCards(cardsToRemove);
		int numberOfWins=0;
		for (int i = 0; i < 1000; i++){
			ArrayList<Player> newPlayers = new ArrayList<Player>();
			for (int j = 0; j<players.size();j++){
				
			}
		}
		return handStrength;
	}
}
