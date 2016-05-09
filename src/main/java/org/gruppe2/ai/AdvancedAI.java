package org.gruppe2.ai;

import org.gruppe2.game.*;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;

import java.util.Arrays;
import java.util.List;

public class AdvancedAI implements AI {
    List<Card> cards;
    List<Card> communityCards;
    PossibleActions possibleActions;

    @Override
    public void doAction(Player player, RoundPlayer roundPlayer, RoundHelper roundHelper, GameHelper gameHelper) {
        this.cards = Arrays.asList(roundPlayer.getCards());
        this.communityCards = roundHelper.getCommunityCards();
        this.possibleActions = roundHelper.getPlayerOptions(player.getUUID());
        
        //Now we just fold
        player.getAction().set(new Action.Fold());
    }

}
