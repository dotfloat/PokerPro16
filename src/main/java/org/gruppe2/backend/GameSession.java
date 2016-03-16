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

    public GameSession() {

    }

    public GameSession(int smallBlind, int bigBlind) {
        this.smallBlindAmount = smallBlind;
        this.bigBlindAmount = bigBlind;
    }

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
        client.setSession(this);
        Player player = new Player(name, 100, client);
        players.add(player);
    }

    private void matchLoop() {
        startNewMatch();
        button = 0;
        smallBlind = 1;
        bigBlind = 2;

        Player smallBlindPayer = activePlayers.get(smallBlind);
        doPlayerAction(new Action.PaySmallBlind(), smallBlindPayer);
        //  notifyOtherPlayersAboutAction(smallBlindPayer, blind);

        Player bigBlindPayer = activePlayers.get(bigBlind);
        doPlayerAction(new Action.PayBigBlind(), bigBlindPayer);
    //    notifyOtherPlayersAboutAction(bigBlindPayer, blind);

        notifyRoundStart();

        for (int i = 0; i < 4; i++){
            table.drawCommunityCards(i);

            notifyAllPlayersAboutCommunityCards(table.getCommunityCards());

            turnLoop();

            if (numActivePlayers() == 1) {
                for (Player p : activePlayers) {
                    if (p != null) {
                        p.addToBank(table.getPot());
                        table.resetPot();
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

        for (int last = button; true; last--) {
            if (last < 0)
                last = activePlayers.size()-1;
            if (activePlayers.get(last) != null) {
                lastRaiserIndex = last;
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

            if (action instanceof Action.Fold) {
                activePlayers.set(currentPlayerIdx, null);
            }
            else if (action instanceof Action.Raise) {
                doPlayerAction(action, player);
                lastRaiserIndex = currentPlayerIdx;
            }
            else if (action instanceof Action.Call)
                doPlayerAction(action, player);

            notifyAllPlayersAboutAction(player, action);

            if (lastRaiserIndex == currentPlayerIdx && !(action instanceof Action.Raise))
                break;

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void startNewMatch(){
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

    private void notifyAllPlayersAboutAction(Player player, Action action){
        for(Player playerToNotify : players)
            playerToNotify.getClient().onPlayerAction(player, action);
    }

    private void notifyAllPlayersAboutCommunityCards(List<Card> communityCards){
        for (Player playersToNotify : players)
            playersToNotify.getClient().onCommunalCards(communityCards);
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
                table.addToPot(raise);
            }
            else if (action instanceof Action.AllIn){
                int raise = player.getBank();
                player.setBank(0);
                player.setBet(player.getBet() + raise);
                table.addToPot(raise);
                highestBet = player.getBet();
            }
            else if (action instanceof Action.PayBigBlind){
                player.setBank(player.getBank() - bigBlindAmount);
                player.setBet(bigBlindAmount);
                table.addToPot(bigBlindAmount);
                highestBet = bigBlindAmount;
            }
            else if(action instanceof Action.PaySmallBlind){
                player.setBank(player.getBank() - smallBlindAmount);
                player.setBet(smallBlindAmount);
                table.addToPot(smallBlindAmount);
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
            if (raise < 1 || raise > player.getBank() + player.getBet() - highestBet)
                return false;
            return pa.canRaise();
        }
        else if (action instanceof Action.Call)
            return pa.canCall();
        else if (action instanceof Action.Fold || action instanceof Action.PayBigBlind || action instanceof  Action.PaySmallBlind || action instanceof Action.AllIn)
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
        if (player.getBank() >= highestBet - player.getBet()){
            if (highestBet - player.getBet() != 0)
                actions.setCall();
        }
        int maxRaise = player.getBank() + player.getBet() - highestBet;
        if (maxRaise > 0)
            actions.setRaise(1, maxRaise);

        return actions;
    }

    public boolean playerHasFolded(Player player){
        for (Player p : activePlayers)
            if (player.equals(p))
                return false;

        return true;
    }
}
