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
        fail();
    }

    @Test
    public void addTooManyPlayersTest() {
        fail();
    }

    @Test
    public void gameViewTest() {
        fail();
    }

    @Test
    public void registerToPlayerTest() {
        fail();
    }
}