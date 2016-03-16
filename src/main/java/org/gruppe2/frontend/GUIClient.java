package org.gruppe2.frontend;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;

import org.gruppe2.backend.Action;
import org.gruppe2.backend.Card;
import org.gruppe2.backend.GameClient;
import org.gruppe2.backend.GameSession;
import org.gruppe2.backend.Player;

public class GUIClient extends GameClient {
	private GUI gui;
	private volatile Action action = null;

	public GUIClient(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void onRoundStart() {
		Platform.runLater(() -> {
			gui.getMainFrame().clearCommunityCards();
			System.out.println("roundStartTest");
		});
	}

	@Override
	public Action onTurn(Player player){
//		gui.updateGUI(player);
		Action action = null;
		System.out.println("your turn player");
		while ((action = getAction()) == null) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
//		gui.updateGUI(player);
		
		setAction(null);

		System.out.println("Action: " + action);

		return action;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	@Override
	public void onCommunalCards(List<Card> communalCards){
		ArrayList<Card> communityCards =  (ArrayList<Card>) communalCards;
		Platform.runLater(new Runnable(){
		    @Override
		    public void run() {
		    	gui.getMainFrame().showCommunityCards(communityCards);
		    }});
	}
	@Override
	public void onPlayerAction(Player player, Action action){
		gui.updateGUI(player);
	}
}
