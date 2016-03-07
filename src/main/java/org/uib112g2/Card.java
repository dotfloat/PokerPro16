package org.uib112g2;

public class Card {
    private int face = 0;

    public int getFace() {
        return face;
    }

    public void setFace(int face) {
        if (face < 0 || face >= 52) {
            throw new IllegalArgumentException();
        }

        this.face = face;
    }
}
