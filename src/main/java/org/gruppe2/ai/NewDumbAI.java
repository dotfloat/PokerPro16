package org.gruppe2.ai;

import org.gruppe2.game.Action;
import org.gruppe2.game.Player;
import org.gruppe2.game.event.PlayerActionQuery;
import org.gruppe2.game.old.PossibleActions;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Query;

import java.util.ArrayList;
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

        final int call = 0;
        final int check = 1;
        final int raise = 2;
        final int fold = 3;

        Random rand = new Random();
        ArrayList<Integer> types = new ArrayList<>();
        PossibleActions actions = model.getOptions();


        if (actions.canCall()) {
            for (int i = 0; i < 8; i++)
                types.add(call);
        }

        if (actions.canCheck()) {
            for (int i = 0; i < 8; i++)
                types.add(check);
        }

        if (actions.canRaise()) {
            for (int i = 0; i < 3; i++)
                types.add(raise);
        }

        types.add(fold);

        switch (types.get(rand.nextInt(types.size()))) {
            case call:
                model.getAction().set(new Action.Call());
                return;

            case check:
                model.getAction().set(new Action.Check());
                return;

            case raise:
                if (actions.getMinRaise() == actions.getMaxRaise())
                    model.getAction().set(new Action.Raise(actions.getMaxRaise()));
                int maxRaiseAmount = actions.getMaxRaise();
                double smartRaise = rand.nextDouble();
                if (smartRaise <= 0.90)
                    maxRaiseAmount = (int) (Math.ceil(maxRaiseAmount * 0.05));
                else if (smartRaise > 0.90 && smartRaise <= 0.999)
                    maxRaiseAmount = (int) (Math.ceil(maxRaiseAmount * 0.20));
                if (maxRaiseAmount == actions.getMaxRaise())
                    model.getAction().set(new Action.Raise(maxRaiseAmount));
                model.getAction().set(new Action.Raise(
                        rand.nextInt(maxRaiseAmount - actions.getMinRaise()) + actions.getMinRaise()));
                return;

            default:
                model.getAction().set(new Action.Fold());
                return;
        }

        //model.getAction().set(new Action.Call());
    }
}
