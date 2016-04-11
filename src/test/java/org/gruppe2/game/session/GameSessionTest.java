package org.gruppe2.game.session;

import org.gruppe2.game.GameBuilder;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.model.PlayerModel;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class GameSessionTest {
    private SessionContext context;
    private PlayerModel model = new PlayerModel(UUID.randomUUID(), "Zohar", "zohar");

    @Before
    public void setup() {
        context = new GameBuilder().maxPlayers(2).start();
    }

    /**
     * Adding a new player shouldn't be a problem
     */
    @Test
    public void addPlayerTest() {
        assertTrue(context.addPlayer(new MockPlayerController(context, model)));
    }

    @Test
    public void addTooManyPlayersTest() {
        for (int i = 0; i < context.getMaxPlayers(); i++) {
            assertTrue(context.addPlayer(new MockPlayerController(context, model)));
        }

        assertFalse(context.addPlayer(new MockPlayerController(context, model)));
    }

    @Test
    public void gameViewTest() throws InterruptedException {
        final boolean[] received = {false};

        context.getGame().onPlayerJoin((PlayerJoinEvent event) -> received[0] = true);

        context.addPlayer(new MockPlayerController(context, model));

        Thread.sleep(100);

        assertTrue(received[0]);
    }
}