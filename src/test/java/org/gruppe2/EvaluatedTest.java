package org.gruppe2;

import static org.junit.Assert.*;

import java.util.Random;

import org.gruppe2.backend.Evaluated;
import org.gruppe2.backend.ShowdownEvaluator.Hand;
import org.junit.Test;

public class EvaluatedTest {

	@Test
	public void testEvaluatedCompare(){
		Random r = new Random();
		for (int i =0;i<10000;i++){
		Evaluated ev = new Evaluated();
		Evaluated ev2 = new Evaluated();
		int[] high = new int[3];
		high[0]=14;
		int[] high2 = new int[3];
		high[0]=r.nextInt(14+1);
		high[1]=r.nextInt(14+1);
		high[2]=r.nextInt(14+1);
		ev.addHand(Hand.ROYALFLUSH, high);
		ev2.addHand(Hand.FOUROFAKIND, high2);
		assert(ev.compareTo(ev2)==1);
		}
	}

}