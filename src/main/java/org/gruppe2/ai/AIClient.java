package org.gruppe2.ai;

import org.gruppe2.game.old.Action;
import org.gruppe2.game.old.GameClient;
import org.gruppe2.game.old.Player;
import org.gruppe2.game.old.PossibleActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AIClient extends GameClient {
    private static final List<String> names = Arrays.asList("Anne", "Bob", "Chuck", "Dennis", "Emma", "McGyver","NEO","LINK","ZELDA","IRON MAN","Mario","MAO","STAIL","Putin");
    private Difficulty difficulty = Difficulty.ADVANCED;

    public AIClient() {
        Random rand = new Random();
        setName(names.get(rand.nextInt(names.size())));
    }
    
    public void setDifficulty(Difficulty difficulty){
    	this.difficulty=difficulty;
    }

    @Override
    public Action onTurn(Player player) {

        final int call = 0;
        final int check = 1;
        final int raise = 2;
        final int fold = 3;

        Random rand = new Random();
        ArrayList<Integer> types = new ArrayList<>();
        PossibleActions actions = getSession().getPlayerOptions(player);
        
        if (difficulty == Difficulty.ADVANCED){
       
        double handStrength = AIHandCalculator.getHandStrength(player.getClient().getSession().getTable(),player);
		double bank = player.getBank();
		double toRaise = player.getClient().getSession().getHighestBet() - player.getBet();
		if (toRaise>bank)
			toRaise=bank;
		double rateOfReturn = 0;
		if (toRaise != 0 && bank!=0) {
			rateOfReturn = handStrength / Math.max(0.2, toRaise / bank);
			
		}
		Action act = chooseAction(rateOfReturn,actions,bank,handStrength,player);
		return act;
        } else if (difficulty == Difficulty.RANDOM){
		

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
                return new Action.Call();

            case check:
                return new Action.Check();

            case raise:
                if (actions.getMinRaise() == actions.getMaxRaise())
                    return new Action.Raise(actions.getMaxRaise());
                int maxRaiseAmount = actions.getMaxRaise();
                double smartRaise = rand.nextDouble();
                if (smartRaise <= 0.90)
                    maxRaiseAmount = (int) (Math.ceil(maxRaiseAmount * 0.05));
                else if (smartRaise > 0.90 && smartRaise <= 0.999)
                    maxRaiseAmount = (int) (Math.ceil(maxRaiseAmount * 0.20));
                if (maxRaiseAmount <= actions.getMaxRaise())
                    return new Action.Raise(maxRaiseAmount);
                return new Action.Raise(
                        rand.nextInt(maxRaiseAmount - actions.getMinRaise()) + actions.getMinRaise());

            default:
                return new Action.Fold();
        }
        }
        return new Action.Fold();
    }

    public Action chooseAction(double rateOfReturn, PossibleActions actions, double bank, double handStrength, Player player) {
        Random r = new Random();
        int random = r.nextInt(100) + 1;
        
        if (rateOfReturn == 0) {
            if (handStrength > 0.6) {
                if (actions.canRaise()) {
                    return new Action.Raise(this.getSession().getBigBlindAmount());
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
        
        
        if (rateOfReturn < 0.8) {
            if (actions.canCheck())
                return new Action.Check();
            if (random > 95) {
                if (actions.canRaise()) {
                    if (actions.getMaxRaise() > 10 * this.getSession().getBigBlindAmount())
                        return new Action.Raise(this.getSession().getBigBlindAmount());
                }
            } else
                return new Action.Fold();
            
            
        } else if (rateOfReturn < 1) {
            if (actions.canCheck()) {
                return new Action.Check();
            }
            if (random > 80 && random <= 95) {
                if (actions.canRaise() && actions.getMaxRaise() > 8 * this.getSession().getBigBlindAmount()) {
                    return new Action.Raise(this.getSession().getBigBlindAmount());
                }
            }
            if (random > 95) {
                if (actions.canCall())
                    return new Action.Call();
            }
            return new Action.Fold();
            
            
        } else if (rateOfReturn <= 1.3) {
            if (random <= 60 && actions.canCall()) {
                return new Action.Call();
            } else if (actions.canRaise() && actions.getMaxRaise() > this.getSession().getBigBlindAmount() * 3) {
                return new Action.Raise(this.getSession().getBigBlindAmount() * 2);
            }
            
            
        } else if (rateOfReturn > 1.3) {
            if (random <= 30) {
                if (actions.canCall())
                    return new Action.Call();
            } else {
                if (actions.canRaise()) {
                    int numberOfBigBlinds = actions.getMaxRaise() / this.getSession().getBigBlindAmount();
                    int randomBlinds = r.nextInt(numberOfBigBlinds) + 1;
                    return new Action.Raise(this.getSession().getBigBlindAmount() * randomBlinds);
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
