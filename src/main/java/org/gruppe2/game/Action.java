package org.gruppe2.game;

import java.io.Serializable;

/**
 * Get
 */
public abstract class Action implements Serializable {

	public static class Fold extends Action {
	}

	public static class Check extends Action {
	}

	public static class Call extends Action {
	}

	public static class Raise extends Action {
		private int amount;

		public Raise(int amount) {
			this.amount = amount;
		}

		public int getAmount() {
			return amount;
		}
		
		@Override
		public String toNetworkString() {
			String raiseString = getClass().getSimpleName();
			raiseString = raiseString.concat(";"+String.valueOf(getAmount()));
			return raiseString;
		}
	}

	public static class AllIn extends Action {
	}

	public static class Blind extends Action {
		private int amount;

		public Blind(int amount) {
			this.amount = amount;
		}

		public int getAmount() {
			return amount;
		}

		@Override
		public String toString() {
			return "Blind";
		}
	}

	public static class Pass extends Action {
	}

	public String toNetworkString() {
		return toString();
	}

	@Override	
	public String toString() {
		return getClass().getSimpleName();
	}
}
