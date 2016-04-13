package org.gruppe2.ui.javafx;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.layout.Pane;

import org.gruppe2.game.old.Action;
import org.gruppe2.game.old.Card;
import org.gruppe2.game.old.GameClient;
import org.gruppe2.game.old.Player;

/**
 * Test Guiplayer
 * 
 * @author htj063
 *
 */
public class GUIPlayer extends GameClient {

	private volatile Action action = null;
	GameWindow gameWindow;
	CommunityCards communityCards;

	public GUIPlayer(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
		communityCards = gameWindow.communityCardsBox;
	}

	@Override
	public void onRoundStart() {
		Platform.runLater(() -> {
			((PlayerCards) gameWindow.playerCards).setPlayerCards(
					gameWindow.gameSession, communityCards);
		});
	}

	@Override
	public Action onTurn(Player player) {
		activateAndDeactivatePlayers(player);
		gameWindow.updateGameWindow(player);
		Action action = null;
		System.out.println("your turn player");
		while ((action = getAction()) == null) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		setAction(null);
		System.out.println("Action: " + action);

		return action;
	}

	private void activateAndDeactivatePlayers(Player player) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				for (Pane playerInfoBox : gameWindow.playerInfoBoxes) {
					if (((PlayerInfoBox) playerInfoBox).getPlayer() == player) {
						((PlayerInfoBox) playerInfoBox).setActive();
					} else
						((PlayerInfoBox) playerInfoBox).setInActive();
				}
			}
		});
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	@Override
	public void onCommunalCards(List<Card> communalCards) {
		ArrayList<Card> communityCards = (ArrayList<Card>) communalCards;
		Platform.runLater(() -> gameWindow.communityCardsBox
				.setCommunityCards(communityCards));
	}

	@Override
	public void onOtherPlayerTurn(Player player) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (gameWindow.playerInfoBoxes == null)
					return;
				activateAndDeactivatePlayers(player);
			}
		});
	}

	/**
	 * Resets frame, this methods needs major cleanup in next iteration.
	 */
	@Override
	public void onRoundEnd() {
		Platform.runLater(() -> {
			communityCards.clearCommunityCards();
		});
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public void onPlayerAction(Player player, Action action) {
		gameWindow.updateGameWindow(player);
	}

	@Override
	public void onPlayerVictory(Player player) {
		Platform.runLater(() -> {
			boolean gameFinished = true;
			for (Player checkPlayer : getSession().getPlayers()) {
				if (checkPlayer != player) {
					if (checkPlayer.getBank() > 0) {
						gameWindow.displayRoundWon(player);
						gameFinished = false;
						break;
					}
				}
			}
			if (gameFinished)
				gameWindow.displayGameWon(player);
		});
		onRoundEnd();
	}
}
