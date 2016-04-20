package org.gruppe2.game;

/**
 * Get
 */
public class Action {
    protected Action() {
        // Action can only be extended
    }

    public static class Fold extends Action {
    }

    public static class Check extends Action {
    }

    public static class Call extends Action {
    }

    public static class Raise extends Action {
        private int amount;

        public Raise(int amount) {
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }
    }

    public static class AllIn extends Action {
    }

    public static class Blind extends Action {
        private int amount;

        public Blind (int amount){
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }
    }

    public static class Pass extends Action {
    }

    @Override
    public String toString() {
        if (this instanceof Action.Raise)
            return "Raise";
        if (this instanceof Action.Call)
            return "Call";
        if (this instanceof Action.Fold)
            return "Fold";
        if (this instanceof Action.AllIn)
            return "AllIn";
        if (this instanceof Action.Check)
            return "Check";
        if (this instanceof Action.Pass)
            return "Pass";
        if (this instanceof Action.Blind)
            return "Blind";

        return "Not an action";
    }
}
