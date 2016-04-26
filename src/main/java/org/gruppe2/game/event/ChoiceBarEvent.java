package org.gruppe2.game.event;

public class ChoiceBarEvent implements Event {
	
	private final String message;
	
	public ChoiceBarEvent(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
}
