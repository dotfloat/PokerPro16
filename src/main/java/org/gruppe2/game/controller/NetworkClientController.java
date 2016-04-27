package org.gruppe2.game.controller;

import org.gruppe2.game.event.NetworkClientEvent;
import org.gruppe2.game.session.Message;

public class NetworkClientController extends AbstractController{
	
	@Message
	public void startNetworkGame(String joinOrCreate, String message){
		addEvent(new NetworkClientEvent(joinOrCreate,message ));
	}
	@Message
	public void sendMessageToServer(String joinOrCreate, String message){
		addEvent(new NetworkClientEvent(joinOrCreate,message ));
	}
	@Message
	public void sendClientPressedStart(String joinOrCreate, String message){
		addEvent(new NetworkClientEvent(joinOrCreate,message ));
	}
	
}
