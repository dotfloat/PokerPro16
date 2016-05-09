package org.gruppe2.ai;

import org.gruppe2.game.*;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AdvancedAI implements AI {
    List<Card> cards;
    List<Card> communityCards;
    PossibleActions possibleActions;

    @Override
    public void doAction(Player player, RoundPlayer roundPlayer, RoundHelper roundHelper, GameHelper gameHelper) {
    	AITurnSimulator turnSim = new AITurnSimulator();
        this.cards = Arrays.asList(roundPlayer.getCards());
        this.communityCards = roundHelper.getCommunityCards();
        this.possibleActions = roundHelper.getPlayerOptions(player.getUUID());
        double bank = player.getBank();
        double handStrength = turnSim.getHandStregth(roundPlayer, roundHelper.getCommunityCards(), 1000, roundHelper.getActivePlayers().size());
        double toRaise = roundHelper.getHighestBet()-roundPlayer.getBet();
		double raiseRatio =1;
		if (roundPlayer.getBet()!=0){
			raiseRatio=Math.max(toRaise/roundPlayer.getBet(),0.5);
		}
		double risk = Math.max(Math.min(toRaise/bank,0.9),0.1);
		double exponentialMax = 1.1;
		double exponentialMin=1-(exponentialMax-1.0);
		double handStrengthExponential = (1/(exponentialMax-handStrength))-exponentialMin;
		double rateOfReturn = 0;
			rateOfReturn = handStrengthExponential /risk;
			rateOfReturn*=(1/raiseRatio);
        //Now we just fold
        player.getAction().set(chooseAction(rateOfReturn,possibleActions,bank,handStrength,gameHelper));
    }
    
    public Action chooseAction(double rateOfReturn, PossibleActions actions, double bank, double handStrength, GameHelper gameHelper) {
        Random r = new Random();
        int random = r.nextInt(100) + 1;
        
        if (rateOfReturn < 0.1) {
            if (handStrength > 0.6) {
                if (actions.canRaise()) {
                    return new Action.Raise(gameHelper.getBigBlind());
                } else if (actions.canCall())
                    return new Action.Call();
                else if (actions.canAllIn())
                	return new Action.AllIn();
            } else {
                if (actions.canCheck())
                    return new Action.Check();
            }
            return new Action.Fold();
        }
        
        
        if (rateOfReturn < 2.5) {
            if (actions.canCheck())
                return new Action.Check();
            if (random > 95) {
                if (actions.canRaise()) {
                    if (actions.getMaxRaise() > 10 * gameHelper.getBigBlind())
                        return new Action.Raise(gameHelper.getBigBlind());
                }
            } else
                return new Action.Fold();
            
            
        } else if (rateOfReturn < 6.5) {
            if (actions.canCheck()) {
                return new Action.Check();
            }
            if (random > 80 && random <= 95) {
                if (actions.canRaise() && actions.getMaxRaise() > 8 * gameHelper.getBigBlind()) {
                    return new Action.Raise(gameHelper.getBigBlind());
                }
            }
            if (random > 95) {
                if (actions.canCall())
                    return new Action.Call();
            }
            return new Action.Fold();
            
            
        } else if (rateOfReturn <= 12) {
            if (random <= 60 && actions.canCall()) {
                return new Action.Call();
            } else if (actions.canRaise() && actions.getMaxRaise() > gameHelper.getBigBlind() * 3) {
                return new Action.Raise(gameHelper.getBigBlind() * 2);
            }
            
            
        } else if (rateOfReturn > 12) {
            if (random <= 30) {
                if (actions.canCall())
                    return new Action.Call();
            } else {
                if (actions.canRaise()) {
                    int numberOfBigBlinds = actions.getMaxRaise() / gameHelper.getBigBlind();
                    int randomBlinds = r.nextInt(numberOfBigBlinds) + 1;
                    return new Action.Raise(gameHelper.getBigBlind() * randomBlinds);
                } else if (actions.canCall())
                    return new Action.Call();
                else if (actions.canCheck()){
                	return new Action.Check();
                }
                return new Action.Fold();
            }
        }
        return new Action.Fold();
    }

}
