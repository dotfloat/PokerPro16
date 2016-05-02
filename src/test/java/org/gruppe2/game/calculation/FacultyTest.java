package org.gruppe2.game.calculation;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

import junit.framework.Assert;

public class FacultyTest {

	@Test
	public void testIfFacultyOfNIncreasesCorrectlyWithN() {
		BigInteger prev = BigInteger.valueOf(1);
		for (int i =2;i<10;i++){
			BigInteger a = Faculty.faculty(i);	
			assertTrue(prev.equals(a.divide(BigInteger.valueOf(i))));
			prev=a;
		}
	}
	
	@Test
	public void testIfFacultyOfZeroReturnsOne(){
		assertTrue(Faculty.faculty(0)==BigInteger.ONE);
	}
	
	@Test
	public void testResults(){
		for (int i =0;i<52;i++){
		}
	}

}