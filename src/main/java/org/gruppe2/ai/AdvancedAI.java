package org.gruppe2.ai;

import org.gruppe2.game.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AdvancedAI implements AI {
	List<Card> cards;
	List<Card> communityCards;
	PossibleActions possibleActions;

	@Override
	public void doAction(Player player, RoundPlayer roundPlayer, GameInfo gameInfo) {
		AITurnSimulator turnSim = new AITurnSimulator();
		this.cards = Arrays.asList(roundPlayer.getCards());
		this.communityCards = gameInfo.getCommunityCards();
		this.possibleActions = gameInfo.getPossibleActions();
		double bank = player.getBank();
		double handStrength = turnSim.getHandStregth(roundPlayer, gameInfo.getCommunityCards(), 1000,
				gameInfo.getActivePlayers().size());
		double toRaise = gameInfo.getHighestBet() - roundPlayer.getBet();
		double raiseRatio = 1;
		if (roundPlayer.getBet() != 0) {
			raiseRatio = Math.max(toRaise / roundPlayer.getBet(), 0.5);
		}
		double risk = Math.max(Math.min(toRaise / bank, 0.8), 0.2);
		double exponentialMax = 1.1;
		double exponentialMin = 1 - (exponentialMax - 1.0);
		double handStrengthExponential = (1 / (exponentialMax - handStrength)) - exponentialMin;
		double rateOfReturn = 0;
		rateOfReturn = handStrengthExponential / risk;
		rateOfReturn *= (1 / raiseRatio);

		rateOfReturn = rateOfReturn + 1;
		Action action = chooseAction(rateOfReturn, possibleActions, bank, handStrength, gameInfo);
		System.out.println(action);
		System.out.println();
		System.out.println();
		player.getAction().set(action);
	}

	public Action chooseAction(double rateOfReturn, PossibleActions actions, double bank, double handStrength,
			GameInfo gameInfo) {
		Random r = new Random();
		int random = r.nextInt(100) + 1;
		System.out.println(actions.canCheck());
		if (rateOfReturn < 0.1) {
			System.out.println(0.1);
			if (handStrength > 0.2) {
				if (actions.canRaise()) {
					return new Action.Raise(gameInfo.getBigBlind());
				} else if (actions.canCall())
					return new Action.Call();
				else if (actions.canAllIn())
					return new Action.AllIn();
				else if (actions.canCheck())
					return new Action.Check();
			} else {
				if (actions.canCheck())
					return new Action.Check();
			}
			return new Action.Fold();
		}

		if (rateOfReturn < 0.6) {
			System.out.println(0.6);
			if (actions.canCheck())
				return new Action.Check();
			if (random > 95) {
				if (actions.canRaise()) {
					if (actions.getMaxRaise() > 10 * gameInfo.getBigBlind())
						return new Action.Raise(gameInfo.getBigBlind());
				}
			} else
				return new Action.Fold();

		}
		if (rateOfReturn < 1.2) {
			System.out.println(1.2);
			if (actions.canCheck()) {
				return new Action.Check();
			}
			if (random > 80 && random <= 95) {
				if (actions.canRaise() && actions.getMaxRaise() > 8 * gameInfo.getBigBlind()) {
					return new Action.Raise(gameInfo.getBigBlind());
				}
			}
			if (random > 95) {
				if (actions.canCall())
					return new Action.Call();
			}
			return new Action.Fold();

		}
		if (rateOfReturn <= 6.0) {
			System.out.println(6.0);
			if (random <= 60 && actions.canCall()) {
				return new Action.Call();
			} else if (actions.canRaise() && actions.getMaxRaise() > gameInfo.getBigBlind() * 3) {
				return new Action.Raise(gameInfo.getBigBlind() * 2);
			}
			else if (actions.canCheck())
				return new Action.Check();

		}
		if (rateOfReturn > 6.0) {
			System.out.println("high");
			if (random <= 30) {
				if (actions.canCall())
					return new Action.Call();
			} else {
				if (actions.canRaise()) {
					int numberOfBigBlinds = actions.getMaxRaise() / gameInfo.getBigBlind();
					int randomBlinds = 0;
					if (numberOfBigBlinds>0)
					randomBlinds = r.nextInt(numberOfBigBlinds) + 1;
					if (randomBlinds==0){
						if (actions.canCall())
							return new Action.Call();
						if (actions.canCheck())
							return new Action.Check();
						return new Action.Fold();
					}
					return new Action.Raise(gameInfo.getBigBlind() * randomBlinds);
				} else if (actions.canCall())
					return new Action.Call();
				else if (actions.canCheck()) {
					return new Action.Check();
				}
				return new Action.Fold();
			}
		}
		return new Action.Fold();
	}

}
