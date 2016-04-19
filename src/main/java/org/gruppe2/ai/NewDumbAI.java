package org.gruppe2.ai;

import org.gruppe2.game.Action;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Query;

import java.util.Random;
import java.util.UUID;

public class NewDumbAI {
    private static final String[] names = { "Alpha", "Bravo", "Charlie", "Delta", "Echo",
                                            "Foxtrot", "Golf", "Hotel", "India", "Juliet",
                                            "Kilo", "Lima", "Mike", "November", "Oscar",
                                            "Papa", "Quebec", "Romeo", "Sierra", "Tango",
                                            "Uniform", "Victor", "Whiskey", "X-Ray", "Yankee",
                                            "Zulu" };

    private final Random rand = new Random();

    public static PlayerModel generateModel() {
        Random rand = new Random();

        return new PlayerModel(UUID.randomUUID(), names[rand.nextInt(names.length)], "ai", new Query<>(), true);
    }

    @Handler
    public void onAction(PlayerActionQuery query) {
        System.out.println("asd");
        PlayerModel p = query.getPlayerModel();
        if (!p.isBot())
            return;

        p.getAction().set(new Action.Call());
    }
}
