package org.gruppe2.ai;

import org.gruppe2.game.Action;
import org.gruppe2.game.Player;
import org.gruppe2.game.PossibleActions;

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

        return new Player(UUID.randomUUID(), names[rand.nextInt(names.length)], "ai", true);
    }

    @Override
    public void doAction(Player model, PossibleActions options) {
        if (!model.isBot())
            return;

        final int call = 0;
        final int check = 1;
        final int raise = 2;
        final int fold = 3;

        Random rand = new Random();
        ArrayList<Integer> types = new ArrayList<>();

        if (options.canCall()) {
            for (int i = 0; i < 8; i++)
                types.add(call);
        }

        if (options.canCheck()) {
            for (int i = 0; i < 8; i++)
                types.add(check);
        }

        if (options.canRaise()) {
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
                if (options.getMinRaise() == options.getMaxRaise())
                    model.getAction().set(new Action.Raise(options.getMaxRaise()));
                int maxRaiseAmount = options.getMaxRaise();
                double smartRaise = rand.nextDouble();
                if (smartRaise <= 0.90)
                    maxRaiseAmount = (int) (Math.ceil(maxRaiseAmount * 0.05));
                else if (smartRaise > 0.90 && smartRaise <= 0.999)
                    maxRaiseAmount = (int) (Math.ceil(maxRaiseAmount * 0.20));
                if (maxRaiseAmount == options.getMaxRaise())
                    model.getAction().set(new Action.Raise(maxRaiseAmount));
                model.getAction().set(new Action.Raise(
                        rand.nextInt(maxRaiseAmount - options.getMinRaise()) + options.getMinRaise()));
                return;

            default:
                model.getAction().set(new Action.Fold());
                return;
        }

        //model.getAction().set(new Action.Call());
    }
}