package org.gruppe2.ai;

import javafx.geometry.Pos;
import org.gruppe2.backend.*;

import java.util.ArrayList;
import java.util.Random;

public class AIClient extends GameClient {
    public AIClient(GameSession session) {
        super(session);
    }

    @Override
    public Action onTurn(Player player) {
        final int call = 0;
        final int check = 1;
        final int raise = 2;

        Random rand = new Random();
        ArrayList<Integer> types = new ArrayList<>();
        PossibleActions actions = getSession().getPlayerOptions(player);

        if (actions.canCall()) {
            types.add(call);
        }

        if (actions.canCheck()) {
            types.add(check);
        }

        if (actions.canRaise()) {
            types.add(raise);
        }

        switch (rand.nextInt(types.size())) {
            case call:
                return new Action.Call();

            case check:
                return new Action.Check();

            case raise:
                return new Action.Raise(rand.nextInt(actions.getMaxRaise() - actions.getMinRaise()) + actions.getMinRaise());

            default:
                return new Action.Fold();
        }
    }
}
