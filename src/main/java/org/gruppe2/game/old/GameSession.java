package org.gruppe2.game.old;

import java.util.ArrayList;
import java.util.List;

public class GameSession {
	private ArrayList<Player> players = new ArrayList<>();
	private ArrayList<Player> activePlayers = new ArrayList<>();
	private ShowdownEvaluator showdownEvaluator = new ShowdownEvaluator();
	private Table table = new Table();
	Logger logger;
	private int smallBlindAmount;
	private int bigBlindAmount;
	private int highestBet;
	private int button;

	public GameSession() {
		button = 0;
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

	/**
	 * Get the current active players
	 * @return current active players
     */
	public List<Player> getActivePlayers() {
		List<Player> activePlayerList = new ArrayList<Player>();
		for (Player p : activePlayers) {
			if (p != null) {
				activePlayerList.add(p);
			}
		}
		return activePlayerList;
	}

	public int getHighestBet() {
		return highestBet;
	}

	/**
	 * Main game loop Notify players about which players turn it is, waits for
	 * that players action and notifies all players about the action
	 */
	public void mainLoop() {
		for (;;) {
			startNewMatch();
			if (activePlayers.size() <= 1)
				break;
			matchLoop();
		}
	}

	public void addPlayer(GameClient client, int startMoney) {
		client.setSession(this);
		String name = client.getName();
		Player player = new Player(client.getName(), startMoney, client);
		players.add(player);
	}

	private void matchLoop() {
		Player winner = null;
		logger = new Logger();
		logger.record("New Game!");
		logger.record("--Stakes--");
		logger.record("Small Blind: " + getSmallBlindAmount());
		logger.record("Big Blind: " + getBigBlindAmount());

		doPlayerAction(new Action.PaySmallBlind(), getSmallBlindPlayer());
		// notifyOtherPlayersAboutAction(smallBlindPayer, blind);

		doPlayerAction(new Action.PayBigBlind(), getBigBlindPlayer());
		// notifyOtherPlayersAboutAction(bigBlindPayer, blind);

		notifyRoundStart();

		for (int i = 0; i < 4; i++) {
			table.drawCommunityCards(i);

			notifyAllPlayersAboutCommunityCards(table.getCommunityCards());

			turnLoop();

			if (numActivePlayers() == 1) {
				for (Player p : activePlayers) {
					if (p != null) {
						winner = p;
						break;
					}
				}
				break;
			}
		}

		if (winner == null)
			winner = showdownEvaluator.getWinnerOfRound(table, activePlayers).get(0);

		winner.addToBank(table.getPot());

		logger.record(winner.getName() + " won the pot!");
		logger.record("Table Pot: " + table.getPot());
		logger.done();

		table.resetPot();
		notifyPlayerVictory(winner);

		notifyRoundEnd();
		button = (button + 1) % activePlayers.size();
	}

	private void turnLoop() {
		int lastRaiserIndex = 0;

		logger.record("New Turn!");
		for (int last = button; true; last--) {
			if (last < 0)
				last = activePlayers.size() - 1;
			if (activePlayers.get(last) != null) {
				lastRaiserIndex = last;
				break;
			}
		}

		for (int current = button; !activePlayers.isEmpty(); current++) {
			int currentPlayerIdx = (current + 1) % activePlayers.size();
			Player player = activePlayers.get(currentPlayerIdx);

			if (player == null)
				continue;

			if (numActivePlayers() == 1) 
				break;
			

			notifyOtherPlayersAboutTurn(player);
			Action action = new Action.Pass();
			if (player.getBank() > 0)
				action = player.getClient().onTurn(player);
			logger.record(player, action);
			
			if (action instanceof Action.Fold) {
				activePlayers.set(currentPlayerIdx, null);
			} else if (action instanceof Action.Raise) {
					doPlayerAction(action, player);
				lastRaiserIndex = currentPlayerIdx;
			} else if (action instanceof Action.Call) {
				doPlayerAction(action, player);
			}

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

	private void startNewMatch() {
		activePlayers = new ArrayList<>();
		for (Player player : players)
			if (player.getBank() >= smallBlindAmount)
				activePlayers.add(player);
		highestBet = 0;
		table.newDeck();
		for (Player p : activePlayers) {
			p.setBet(0);
			p.setCards(table.drawACard(), table.drawACard());
		}
	}

	public int numActivePlayers() {
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
	 *
	 * @param player
	 *            player whose turn it is
	 */
	void notifyOtherPlayersAboutTurn(Player player) {
		for (Player playerToNotify : players) {
			if (!playerToNotify.equals(player)) {
				playerToNotify.getClient().onOtherPlayerTurn(player);
			}
		}
	}

	/**
	 * Notify all players abut the action performed
	 *
	 * @param player
	 *            player who performed the action
	 * @param action
	 *            the action performed
	 */
	void notifyOtherPlayersAboutAction(Player player, Action action) {
		for (Player playerToNotify : players) {
			if (!playerToNotify.equals(player)) {
				playerToNotify.getClient().onOtherPlayerAction(player, action);
			}
		}
	}

	private void notifyAllPlayersAboutAction(Player player, Action action) {
		for (Player playerToNotify : players)
			playerToNotify.getClient().onPlayerAction(player, action);
	}

	private void notifyAllPlayersAboutCommunityCards(List<Card> communityCards) {
		for (Player playersToNotify : players)
			playersToNotify.getClient().onCommunalCards(communityCards);
	}

	// TODO: Code to perform actions

	/**
	 * Perform the action requested by the player
	 *
	 * @param action
	 *            action to perform
	 * @param player
	 *            player performing
	 */
	private void doPlayerAction(Action action, Player player) {
		if (checkLegalAction(action, player)) {
			int raise;
			if (action instanceof Action.Raise) {
				raise = ((Action.Raise) action).getAmount();
				int chipsToMove = (highestBet - player.getBet()) + raise;
				moveChips(player, highestBet + raise, player.getBank()-chipsToMove, chipsToMove);
			} else if (action instanceof Action.Call) {
				raise = highestBet - player.getBet();
				moveChips(player, player.getBet() + raise, player.getBank() - raise, raise);
			} else if (action instanceof Action.AllIn) {
				raise = player.getBank();
				moveChips(player, player.getBet() + raise, 0, raise);
			} else if (action instanceof Action.PayBigBlind) {
				moveChips(player, bigBlindAmount, player.getBank() - bigBlindAmount, bigBlindAmount);
			} else if (action instanceof Action.PaySmallBlind) {
				moveChips(player, smallBlindAmount, player.getBank() - smallBlindAmount, smallBlindAmount);
			}
		} else {
			throw new IllegalArgumentException(player.getName() + " can't " + action + " , is active: " + activePlayers.contains(player));
		}

		if (player.getBet() > highestBet)
			highestBet = player.getBet();
	}

	private void moveChips(Player player, int playerSetBet, int playerSetBank, int addToTablePot){
		player.setBet(playerSetBet);
		player.setBank(playerSetBank);
		table.addToPot(addToTablePot);
	}

	/**
	 * Check if it is a legal action
	 *
	 * @param action
	 *            action being performed
	 * @param player
	 *            player performing
	 * @return true if it's legal, false if not
	 */
	private boolean checkLegalAction(Action action, Player player) {
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
		} else if (action instanceof Action.Call)
			return pa.canCall();
		else if (action instanceof Action.Fold || action instanceof Action.PayBigBlind
				|| action instanceof Action.PaySmallBlind || action instanceof Action.AllIn || action instanceof Action.Pass)
			return true;
		else
			throw new IllegalArgumentException("Not an action");
	}

	public Table getTable() {
		return table;
	}

	/**
	 * Find the possible option to a player
	 *
	 * @param player
	 *            Current player
	 * @return The options available to the player
	 */
	public PossibleActions getPlayerOptions(Player player) {
		PossibleActions actions = new PossibleActions();

		if (player.getBet() == highestBet)
			actions.setCheck();
		if (player.getBank() >= highestBet - player.getBet()) {
			if (highestBet - player.getBet() != 0)
				actions.setCall(highestBet - player.getBet());
		}
		int maxRaise = player.getBank() + player.getBet() - highestBet;
		if (maxRaise > 0)
			actions.setRaise(1, maxRaise);

		if (!actions.canCall() && !actions.canCheck() && !actions.canRaise())
			actions.setAllIn();

		return actions;
	}

	public int getSmallBlindIdx() {
		return (button + 1) % activePlayers.size();
	}

	public int getBigBlindIdx() {
		return (button + 2) % activePlayers.size();
	}

	public Player getSmallBlindPlayer() {
		return activePlayers.get(getSmallBlindIdx());
	}

	public Player getBigBlindPlayer() {
		return activePlayers.get(getBigBlindIdx());
	}

	public boolean playerHasFolded(Player player) {
		for (Player p : activePlayers)
			if (player.equals(p))
				return false;

		return true;
	}
}
