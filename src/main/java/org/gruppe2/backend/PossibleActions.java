package org.gruppe2.backend;

/**
 * Created by Mikal on 14.03.2016.
 */
public class PossibleActions {
    private boolean call;
    private boolean check;
    private boolean raise;
    private int minRaise;
    private int maxRaise;

    public PossibleActions() {
        call = false;
        check = false;
        raise = false;
    }

    public void setCall(){
        call = true;
    }

    public void setCheck(){
        check = true;
    }

    public void setRaise(int minRaise, int maxRaise){
        raise = true;
        this.minRaise = minRaise;
        this.maxRaise = maxRaise;
    }

    public boolean canCall() {
        return call;
    }

    public boolean canCheck() {
        return check;
    }

    public boolean canRaise() {
        return raise;
    }

    public int getMinRaise() {
        return minRaise;
    }

    public int getMaxRaise() {
        return maxRaise;
    }
}
