package org.gruppe2.ai;

import java.util.ArrayList;

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
	public static double getHandStrength(Table table, ArrayList<Player> players, AIClient ai){
		if (table!=null)
			return 0;
		int cardsLeftToPick = 5-table.getCommunityCards().size();
		double handStrength=0;
		
		return handStrength;
	}
}
