package org.gruppe2.game.model;

import org.gruppe2.game.PlayerStatistics;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class StatisticsModel {
    private final Map<UUID, PlayerStatistics> playerStatistics = new ConcurrentHashMap<>();

    public Map<UUID, PlayerStatistics> getPlayerStatistics() {
        return playerStatistics;
    }
}
