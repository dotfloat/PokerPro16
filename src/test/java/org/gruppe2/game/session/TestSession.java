package org.gruppe2.game.session;

import org.gruppe2.game.GameState;
import org.gruppe2.game.controller.AbstractPlayerController;

public class TestSession extends Session {
    private GameState state = GameState.WAITING_FOR_PLAYERS;

    @Override
    GameState getGameState() {
        return state;
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addPlayer(AbstractPlayerController controller) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addPlayerAsync(AbstractPlayerController controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}
}
