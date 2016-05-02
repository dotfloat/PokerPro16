package org.gruppe2.game.calculation;

import java.math.BigInteger;

public class Faculty {
	/**
	 * Calculates faculty
	 * @param n - The number to calculate faculty of.
	 * @return result - Faculty of n as a BigInteger.
	 */
	public static BigInteger faculty(int n){
		if (n<0){
			throw new IllegalArgumentException("n must be positive!");
		}
		BigInteger result = BigInteger.valueOf(1);
		while (n>1){
			result = result.multiply(BigInteger.valueOf(n));
			n--;
		}
		return result;
	}
}
