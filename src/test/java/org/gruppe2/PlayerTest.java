package org.gruppe2;

import org.gruppe2.backend.Player;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by Mikal on 10.03.16.
 */
public class PlayerTest {
    private final String NAME = "TestPlayer";
    private final int START_CHIPS = 50;
    private Player player;

    @Before
    public void setUp() throws Exception {
        player = new Player(NAME, START_CHIPS);
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Returns incorrect name", NAME, player.getName());
    }

    @Test
    public void testGetChipsAtStartOfGame() throws Exception {
        assertEquals("Returns incorrect number of chips", START_CHIPS, player.getChips());
    }

    @Test
    public void testChipsAddedToPlayer() throws Exception {
        int chipsToAdd = 35;
        int expected = player.getChips() + chipsToAdd;
        player.addChips(chipsToAdd);

        assertEquals("Added incorrect number of chips", expected, player.getChips());
    }

    @Test
    public void testAddingNegativeNumberOfChipsShouldReduceTheNumberOfChipsAPlayerHas() throws Exception {
        int chipsToRemove = -35;
        int expected = player.getChips() + chipsToRemove;
        player.addChips(chipsToRemove);

        assertEquals("Removed incorrect number of chips", expected, player.getChips());
    }
}
