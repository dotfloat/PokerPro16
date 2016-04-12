package org.gruppe2.game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoundModel implements Model {
    private final List<TablePlayerModel> activePlayers = Collections.synchronizedList(new ArrayList<>());

    private volatile int pot = 0;

    public List<TablePlayerModel> getActivePlayers() {
        return activePlayers;
    }

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }
}
