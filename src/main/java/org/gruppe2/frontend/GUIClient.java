package org.gruppe2.frontend;

import org.gruppe2.ai.AIClient;
import org.gruppe2.backend.Action;
import org.gruppe2.backend.GameClient;
import org.gruppe2.backend.GameSession;

public class GUIClient extends GameClient implements Runnable {
	GUI gui;
	public GUIClient(GameSession session, GUI gui) {
		super(session);
		this.gui = gui;
		
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	public Action onTurn(){
		while (true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
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
	
	

}
