package org.gruppe2.game.session;

import org.gruppe2.game.GameBuilder;
import org.gruppe2.game.event.PlayerActionEvent;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.view.PlayerView;
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
        assertTrue(context.addPlayer(new MockPlayerController(context)));
    }

    @Test
    public void addTooManyPlayersTest() {
        for (int i = 0; i < context.getMaxPlayers(); i++) {
            assertTrue(context.addPlayer(new MockPlayerController(context)));
        }

        assertFalse(context.addPlayer(new MockPlayerController(context)));
    }

    @Test
    public void gameViewTest() throws InterruptedException {
        final boolean[] received = {false};

        context.getGame().onPlayerJoin((PlayerJoinEvent event) -> received[0] = true);

        context.addPlayer(new MockPlayerController(context));

        Thread.sleep(100);

        assertTrue(received[0]);
    }

    @Test
    public void registerToPlayerTest() {
        final int[] numAccess = {0};

        MockPlayerController player1;
        MockPlayerController player2;
        PlayerView view;

        player1 = new MockPlayerController(context);
        player1.setModel(new PlayerModel(UUID.randomUUID(), "JW", "jw"));
        player1.setView(new PlayerView(player1));

        player2 = new MockPlayerController(context);
        player2.setModel(new PlayerModel(UUID.randomUUID(), "flusha", "flusha"));
        player2.setView(new PlayerView(player2));

        context.addPlayer(player1);
        context.addPlayer(player2);

        if ((view = context.getGame().getPlayerByName("flusha")) != null) {
            view.onAction((PlayerActionEvent event) -> numAccess[0]++);
        } else {
            fail("Couldn't find player name.");
        }
    }
}