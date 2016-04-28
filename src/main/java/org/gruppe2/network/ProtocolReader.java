package org.gruppe2.network;

import java.util.UUID;

import org.gruppe2.game.event.ChatEvent;
import org.gruppe2.game.event.Event;


/**
 * Reader or writer for net protocol
 * @author htj063
 *
 */
public class ProtocolReader {
	
	public static String[] reader(String msg){
		String[] listOfCommands = null;
		if(msg.contains(":")){
			int colonPosition = msg.indexOf(":");
			String preColon = msg.substring(0, colonPosition);
			String postColon = msg.substring(colonPosition+1);
			String[] s = preColon.split(";");
			int size = s.length+1;
			listOfCommands = new String[size];
			
			for(int i=0;i<size;i++){
				if(i==size-1)
					listOfCommands[i] = postColon;
				else
					listOfCommands[i] = s[i];
			}
		}
		else{
			listOfCommands = msg.split(";");
		}
		return listOfCommands;
	}
	/**
	 * Creates an event from the list of commands given by the protocol reader method
	 * @param listOfCommands
	 * @return
	 */
	public static Event parseEvent(String[] listOfCommands){
		
		if(listOfCommands.length > 0){
			switch(listOfCommands[0]){
				case "CHAT":
					UUID playerUUID = UUID.fromString(listOfCommands[1]);
					String message = listOfCommands[2];
					return new ChatEvent(message,playerUUID);
				case "ACTION":
					break;	
			}
		}
		return null;
	}
	
	
}