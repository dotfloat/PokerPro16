package org.gruppe2.game.event;

import java.util.UUID;

public class NetworkServerEvent implements Event {
	
	String joinOrCreate;
	String message;
	public NetworkServerEvent(String joinOrCreate,String message){
		//TODO -->
		this.joinOrCreate = joinOrCreate;
		this.message = message;
	}
	public String getJoinOrCreate(){
		return joinOrCreate;
	}
	public String getMessage(){
		return message;
	}
}
