package org.gruppe2.backend;

import java.util.HashMap;

import org.gruppe2.backend.ShowdownEvaluator.Hand;

public class Evaluated {
	HashMap<Hand, Integer> handAndHighCards = new HashMap<Hand,Integer>();
	public HashMap<Hand,Integer> getHigh(){
		return handAndHighCards;
	}
	
	public int compareTo(Evaluated ev1, Evaluated ev2){
		Hand[] hands = Hand.values(); // ShowdownEvaluator.reverse(Hand.values());
		for (int i =hands.length-1;i>=0;i--){
			if (ev1.getHigh().get(hands[i])>ev2.getHigh().get(hands[i])){
				return 1;
			}else if (ev1.getHigh().get(hands[i])<ev2.getHigh().get(hands[i])){
				return -1;
			}
		}
		return 0;
	}
	
	public void addHand(Hand hand, int high){
		handAndHighCards.put(hand, high);
	}
}
