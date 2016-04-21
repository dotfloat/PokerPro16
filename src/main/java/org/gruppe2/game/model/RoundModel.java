package org.gruppe2.game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RoundModel {
    private final List<RoundPlayerModel> activePlayers = Collections.synchronizedList(new ArrayList<>());

    private volatile boolean playing = false;
    private volatile int current = 0;
    private volatile int pot = 0;
    private volatile int highestBet = 0;

    public List<RoundPlayerModel> getActivePlayers() {
        return activePlayers;
    }

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getHighestBet() {
        return highestBet;
    }

    public void setHighestBet(int highestBet) {
        this.highestBet = highestBet;
    }
}
