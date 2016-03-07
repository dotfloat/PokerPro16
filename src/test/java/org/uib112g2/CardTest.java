package org.uib112g2;

import org.junit.*;
import static org.junit.Assert.*;

public class CardTest {
    Card card = new Card();

    @Before
    public void setup() {

    }

    @Test
    public void testDefaultIsZero() {
        assertEquals(card.getFace(), 0);
    }

    @Test
    public void testGetSet() {
        card.setFace(10);
        assertEquals(card.getFace(), 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFaceException() {
        card.setFace(100);
    }
}