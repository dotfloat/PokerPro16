package org.gruppe2.game.session;

import org.gruppe2.game.GameBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameSessionTest {
    SessionContext context;

    @Before
    public void setup() {
        context = new GameBuilder().maxPlayers(2).start();
    }

    /**
     * Adding a new player shouldn't be a problem
     */
    @Test
    public void addPlayerTest() {
        MockPlayerController player = new MockPlayerController();
        context.addPlayer(player);

        assertEquals(AsyncStatus.COMPLETED, player.getAsyncStatus());
    }

    @Test
    public void addTooManyPlayersTest() {
        MockPlayerController player;

        for (int i = 0; i < context.getMaxPlayers(); i++) {
            player = new MockPlayerController();

            context.addPlayer(player);

            assertEquals(AsyncStatus.COMPLETED, player.getAsyncStatus());
        }

        player = new MockPlayerController();

        context.addPlayer(player);

        assertEquals(AsyncStatus.FAILED, player.getAsyncStatus());
    }
}