package org.gruppe2.backend;

import java.util.HashMap;
import org.gruppe2.backend.ShowdownEvaluator.Hand;

public class Evaluated implements Comparable<Evaluated> {
	private HashMap<Hand, int[]> handAndHighCards = new HashMap<Hand,int[]>();
	private Hand hand;
	private HashMap<Hand,int[]> getHigh(){
		return handAndHighCards;
	}
	
	public void setHand(Hand hand){
		this.hand=hand;
	}
	
	
	public Hand getHand(){
		return hand;
	}
	
	/**
	 * Compares one Evaluated instance with another
	 * For example it returns 1 if ev1 is greater than ev2
	 * @param ev1
	 * @param ev2
	 * @return 1 if greater, 0 if equals, -1 if less
	 */
	@Override
	public int compareTo(Evaluated ev2) {
		if (ev2==null)
			return 1;
		Hand[] hands = Hand.values(); // ShowdownEvaluator.reverse(Hand.values());
		for (int i =hands.length-1;i>=0;i--){
			if (this.getHigh().get(hands[i])==null && ev2.getHigh().get(hands[i])== null)
				continue;
			if (this.getHigh().get(hands[i])!=null && ev2.getHigh().get(hands[i])== null){
				return 1;
			}else if (this.getHigh().get(hands[i])==null && ev2.getHigh().get(hands[i]) != null){
				return -1;
			} else {
				int[] first = this.getHigh().get(hands[i]);
				int[] second = ev2.getHigh().get(hands[i]);
				for (int j =0;j<first.length && j<second.length;j++){
					if (first[j]>second[j])
						return 1;
					if (first[j]<second[j])
						return -1;
					continue;
				}
				return 0;
				
			}
		}
		return 0;
	}
	public void addHand(Hand hand, int[] high){
		handAndHighCards.put(hand, high);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((handAndHighCards == null) ? 0 : handAndHighCards.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evaluated other = (Evaluated) obj;
		if (handAndHighCards == null) {
			if (other.handAndHighCards != null)
				return false;
		} else if (!handAndHighCards.equals(other.handAndHighCards))
			return false;
		return true;
	}
	
	@Override
	public String toString(){
		return new String(hand.toString());
	}

}
