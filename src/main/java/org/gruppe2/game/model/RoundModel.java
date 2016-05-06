package org.gruppe2.game.model;

import org.gruppe2.game.Card;
import org.gruppe2.game.RoundPlayer;

import java.io.Serializable;
import java.util.*;

public class RoundModel implements Serializable {
    private final List<RoundPlayer> activePlayers = Collections.synchronizedList(new ArrayList<>());
    private final List<Card> communityCards = Collections.synchronizedList(new ArrayList<>());
    private final Map<UUID, Integer> raiseMap = Collections.synchronizedMap(new HashMap<>());

    private volatile boolean playing = false;
    private volatile int current = 0;
    private volatile int pot = 0;
    private volatile int highestBet = 0;
    private volatile int roundNumber = 0;
    private volatile UUID lastRaiserID = null;

    public List<RoundPlayer> getActivePlayers() {
        return activePlayers;
    }

    public List<Card> getCommunityCards() {
        return communityCards;
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

    public int getNumberOfActivePlayers() {
        return activePlayers.size();
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public UUID getLastRaiserID() {
        return lastRaiserID;
    }

    public void setLastRaiserID(UUID lastRaiserID) {
        this.lastRaiserID = lastRaiserID;
    }

    public Map<UUID, Integer> getRaiseMap() {
        return raiseMap;
    }

    public synchronized void apply(RoundModel object) {
        activePlayers.clear();
        activePlayers.addAll(object.activePlayers);

        communityCards.clear();
        communityCards.addAll(object.communityCards);

        raiseMap.clear();
        raiseMap.putAll(object.raiseMap);

        playing = object.playing;
        current = object.current;
        pot = object.pot;
        highestBet = object.highestBet;
        roundNumber = object.roundNumber;
        lastRaiserID = object.lastRaiserID;
    }
}
