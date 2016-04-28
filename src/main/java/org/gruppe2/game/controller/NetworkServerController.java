package org.gruppe2.game.controller;

import org.gruppe2.game.event.NetworkServerEvent;
import org.gruppe2.game.session.Message;


public class NetworkServerController extends AbstractController{
	
	@Message
	public void startNetworkGame(String joinOrCreate, String message){
		addEvent(new NetworkServerEvent(joinOrCreate,message ));
	}
	@Message
	public void sendMessageToClients(String joinOrCreate, String message){
		addEvent(new NetworkServerEvent(joinOrCreate,message ));
	}
	
}


