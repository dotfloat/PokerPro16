package org.gruppe2.ai;

import org.gruppe2.game.Action;
import org.gruppe2.game.Player;
import org.gruppe2.game.session.Query;

import java.util.Random;
import java.util.UUID;

public class NewDumbAI implements AI{
    private static final String[] names = { "Alpha", "Bravo", "Charlie", "Delta", "Echo",
                                            "Foxtrot", "Golf", "Hotel", "India", "Juliet",
                                            "Kilo", "Lima", "Mike", "November", "Oscar",
                                            "Papa", "Quebec", "Romeo", "Sierra", "Tango",
                                            "Uniform", "Victor", "Whiskey", "X-Ray", "Yankee",
                                            "Zulu" };

    private final Random rand = new Random();

    public static Player generateModel() {
        Random rand = new Random();

        return new Player(UUID.randomUUID(), names[rand.nextInt(names.length)], "ai", new Query<>(), true);
    }

    @Override
    public void doAction(Player model) {
        if (!model.isBot())
            return;

        model.getAction().set(new Action.Call());
    }
}
