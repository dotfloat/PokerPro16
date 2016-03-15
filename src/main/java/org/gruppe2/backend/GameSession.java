package org.gruppe2.backend;

import java.util.ArrayList;
import java.util.List;

public class GameSession {
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Player> activePlayers = new ArrayList<>();
    private Table table = new Table();
    private int smallBlindAmount = 5;
    private int bigBlindAmount = 10;
    private int highestBet;
    private int button;
    private int smallBlind;
    private int bigBlind;

    public int getSmallBlindAmount() {
        return smallBlindAmount;
    }

    public int getBigBlindAmount() {
        return bigBlindAmount;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getHighestBet() {
        return highestBet;
    }

    /**
     * Main game loop
     * Notify players about which players turn it is, waits for that players action and notifies all players about the action
     */
    public void mainLoop() {
        for (;;) {
            matchLoop();
        }
    }

    public void addPlayer(String name, GameClient client) {
        Player player = new Player(name, 100, client);
        players.add(player);
    }

    private void matchLoop() {
        StartNewMatch();
        button = 0;
        smallBlind = 1;
        bigBlind = 2;

        //Need blind action
        Action.Raise blind;
        Player smallBlindPayer = activePlayers.get(smallBlind);
        blind = new Action.Raise(smallBlindAmount);
        doPlayerAction(blind, smallBlindPayer);
        notifyOtherPlayersAboutAction(smallBlindPayer, blind);

        Player bigBlindPayer = activePlayers.get(bigBlind);
        blind = new Action.Raise(bigBlindAmount);
        doPlayerAction(blind, bigBlindPayer);
        notifyOtherPlayersAboutAction(bigBlindPayer, blind);

        highestBet = bigBlindAmount; //Temporary fix
        table.addToPot(-5); //Temporary fix
        notifyRoundStart();

        for (int i = 0; i < 4; i++){
            table.drawCommunityCards(i);

            players.get(button).getClient().onCommunalCards(table.getCommunityCards());

            turnLoop();

            if (numActivePlayers() == 1) {
                for (Player p : activePlayers) {
                    if (p != null) {
                        notifyPlayerVictory(p);
                        break;
                    }
                }
                break;
            }
        }

        notifyRoundEnd();
    }

    private void turnLoop() {
        int lastRaiserIndex = 0;

        for (int first = smallBlind; first < activePlayers.size(); first++) {
            if (activePlayers.get(first) != null) {
                lastRaiserIndex = first-1;
                break;
            }
        }

        for (int current = smallBlind-1; !activePlayers.isEmpty(); current++) {
            int currentPlayerIdx = (current + 1) % activePlayers.size();
            Player player = activePlayers.get(currentPlayerIdx);

            if (player == null)
                continue;

            if (numActivePlayers() == 1)
                break;

            notifyOtherPlayersAboutTurn(player);
            Action action = player.getClient().onTurn(player);
            notifyOtherPlayersAboutAction(player, action);

            if (action instanceof Action.Fold) {
                activePlayers.set(currentPlayerIdx, null);
            }
            else if (action instanceof Action.Raise) {
                doPlayerAction(action, player);
                lastRaiserIndex = currentPlayerIdx;
            }
            else if (action instanceof Action.Call)
                doPlayerAction(action, player);

            if (lastRaiserIndex == currentPlayerIdx && !(action instanceof Action.Raise))
                break;

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void StartNewMatch(){
        activePlayers = new ArrayList<>();
        activePlayers.addAll(players);
        highestBet = 0;
        table.resetPot();
        table.newDeck();
        for (Player p : activePlayers){
            p.setBet(0);
            p.setCards(table.drawACard(), table.drawACard());
        }
    }

    private int numActivePlayers() {
        int numActivePlayers = 0;

        for (Player p : activePlayers) {
            if (p != null) {
                numActivePlayers++;
            }
        }

        return numActivePlayers;
    }

    void notifyRoundStart() {
        for (Player p : players) {
            p.getClient().onRoundStart();
        }
    }

    void notifyRoundEnd() {
        for (Player p : players) {
            p.getClient().onRoundEnd();
        }
    }

    void notifyPlayerVictory(Player winner) {
        for (Player p : players) {
            p.getClient().onPlayerVictory(winner);
        }
    }

    /**
     * Notify all players about whose turn it is
     * @param player player whose turn it is
     */
    void notifyOtherPlayersAboutTurn(Player player) {
        for(Player playerToNotify : players) {
            if(!playerToNotify.equals(player)) {
                playerToNotify.getClient().onOtherPlayerTurn(player);
            }
        }
    }

    /**
     * Notify all players abut the action performed
     * @param player player who performed the action
     * @param action the action performed
     */
    void notifyOtherPlayersAboutAction(Player player, Action action) {
        for(Player playerToNotify : players) {
            if(!playerToNotify.equals(player)) {
                playerToNotify.getClient().onOtherPlayerAction(player, action);
            }
        }
    }

    //TODO: Code to perform actions
    /**
     * Perform the action requested by the player
     * @param action action to perform
     * @param player player performing
     */
    void doPlayerAction(Action action, Player player) {
        if (checkLegalAction(action, player)) {
            if (action instanceof Action.Raise){
                int raise = ((Action.Raise) action).getAmount();
                int chipsToMove = (highestBet - player.getBet()) + raise;
                player.setBet(highestBet + raise);
                player.setBank(player.getBank() - chipsToMove);
                table.addToPot(chipsToMove);
                highestBet = player.getBet();
            }
            else if (action instanceof Action.Call){
                int raise = highestBet - player.getBet();
                player.setBet(player.getBet() + raise);
                player.setBank(player.getBank() - raise );
                table.addToPot(table.getPot() + raise);
            }
            else if (action instanceof Action.AllIn){
                int raise = player.getBank();
                player.setBank(0);
                player.setBet(player.getBet() + raise);
                table.addToPot(raise);
                highestBet = player.getBet();
            }
            else if (action instanceof Action.PayBigBlind){
                player.setBank(player.getBank() - bigBlind);
                player.setBet(bigBlind);
                table.addToPot(bigBlind);
                highestBet = bigBlind;
            }
            else if(action instanceof Action.PaySmallBlind){
                player.setBank(player.getBank() - smallBlind);
                player.setBet(smallBlind);
                table.addToPot(smallBlind);
            }
        }
        else {
            throw new IllegalArgumentException(player.getName() + " can't do that action");
        }
    }

    /**
     * Check if it is a legal action
     * @param action action being performed
     * @param player player performing
     * @return true if it's legal, false if not
     */
    boolean checkLegalAction(Action action, Player player) {
        if (!activePlayers.contains(player))
            return false;

        PossibleActions pa = getPlayerOptions(player);
        if (action instanceof Action.Check)
            return pa.canCheck();
        else if (action instanceof Action.Raise) {
            int raise = ((Action.Raise) action).getAmount();
            if (raise < 1 || raise > player.getBank() - (player.getBet() + highestBet))
                return false;
            return pa.canRaise();
        }
        else if (action instanceof Action.Call)
            return pa.canCall();
        else if (action instanceof Action.Fold)
            return true;
        else
            throw new IllegalArgumentException("Not an action");
    }

    public Table getTable() {
        return table;
    }

    /**
     * Find the possible option to a player
     * @param player Current player
     * @return The options available to the player
     */
    public PossibleActions getPlayerOptions (Player player){
        PossibleActions actions = new PossibleActions();

        if (player.getBet() == highestBet)
            actions.setCheck();
        if (player.getBank() > highestBet - player.getBet()){
            int maxRaise = player.getBank() - (player.getBet() + highestBet);
            if (!(player.getBank() == highestBet - player.getBet()) && maxRaise > 0)
                actions.setRaise(1, maxRaise);
            actions.setCall();
        }

        return actions;
    }
}
