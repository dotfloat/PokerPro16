package org.gruppe2.network;

import java.util.UUID;

public class TableEntry {
	UUID uuid;
	int currentPlayers;
	int maxPlayers;
	
	public TableEntry(UUID uuid,int currentPlayers, int maxPlayers){
		this.uuid = uuid;
		this.currentPlayers = currentPlayers;
		this.maxPlayers = maxPlayers;
		
	}
	
	public UUID getUUID(){
		return uuid;
	}
	public int getMaxPlayers(){
		return maxPlayers;
	}
	public int getCurrentPlayers(){
		return currentPlayers;
	}

}
