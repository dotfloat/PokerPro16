package org.gruppe2.game.event;

public class ChatEvent implements Event {
	
	private final String message;
	
	public ChatEvent(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
}
