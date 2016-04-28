package org.gruppe2.network;


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
			
			for(int i=0;i<=size;i++){
				if(i==size)
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
	
	
}