package org.gruppe2.game.controller;

import org.gruppe2.game.event.ChatEvent;
import org.gruppe2.game.session.Message;

public class ChatController extends AbstractController {

	@Message
	public void chat(String message){
		addEvent(new ChatEvent(message));
	}
	
}
