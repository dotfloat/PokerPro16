package org.gruppe2.game.session;

import org.gruppe2.game.GameState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SessionContextTest {
    SessionContext context;

    @Before
    public void setup() {
        context = Session.start(TestSession.class);
    }

    @Test
    public void sessionStartTest() {
        assertEquals(GameState.WAITING_FOR_PLAYERS, context.getGameState());
    }

    @Test
    public void spectatorCountTest() {
        fail("Implement pls");
    }
}