package org.gruppe2.frontend;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.gruppe2.ai.AIClient;
import org.gruppe2.backend.Action;
import org.gruppe2.backend.GameClient;
import org.gruppe2.backend.GameSession;
import org.gruppe2.backend.Player;

public class GUIClient extends GameClient implements Runnable {
	private GUI gui;
	private volatile Action action = null;

	public GUIClient(GameSession session, GUI gui) {
		super(session);
		this.gui = gui;
	}

	@Override
	public void onRoundStart() {
		Platform.runLater(() -> new Alert(Alert.AlertType.INFORMATION, "Round started").showAndWait());
	}

	@Override
	public Action onTurn(Player player){
		Action action = null;

		while ((action = getAction()) == null) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		setAction(null);

		System.out.println("Action: " + action);

		return action;
	}
	@Override
	public void run() {
		getSession().addPlayer("CoolestPerson", this);
		getSession().addPlayer("Anne", new AIClient(getSession()));
		getSession().addPlayer("Bob", new AIClient(getSession()));
        getSession().addPlayer("Chuck", new AIClient(getSession()));
        getSession().addPlayer("Dennis", new AIClient(getSession()));
        getSession().addPlayer("Emma", new AIClient(getSession()));
        getSession().mainLoop();
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
}
