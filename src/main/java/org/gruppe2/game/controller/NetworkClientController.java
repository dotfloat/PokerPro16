package org.gruppe2.game.controller;

import org.gruppe2.game.event.NetworkClientEvent;
import org.gruppe2.game.session.Message;

public class NetworkClientController extends AbstractController{
	
	@Message
	public void startNetworkGame(String joinOrCreate){
		addEvent(new NetworkClientEvent(joinOrCreate));
	}
}
