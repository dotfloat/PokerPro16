package org.gruppe2.ai;

import org.gruppe2.game.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AdvancedAI implements AI {
    List<Card> cards;
    List<Card> communityCards;

    @Override
    public void doAction(Player player, RoundPlayer roundPlayer, PossibleActions options, List<Card> communityCards) {
        this.cards = Arrays.asList(roundPlayer.getCards());
        this.communityCards = communityCards;

        //Now we just fold
        player.getAction().set(new Action.Fold());
    }

}
