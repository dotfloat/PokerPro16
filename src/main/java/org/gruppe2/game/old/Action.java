package org.gruppe2.game.old;

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

    public static class PaySmallBlind extends Action {
    }

    public static class PayBigBlind extends Action {
    }
}
