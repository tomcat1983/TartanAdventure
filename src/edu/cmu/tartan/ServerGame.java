package edu.cmu.tartan;

import org.eclipse.jdt.annotation.NonNull;

import edu.cmu.tartan.GameInterface.MessageType;
import edu.cmu.tartan.games.InvalidGameException;
import edu.cmu.tartan.xml.GameMode;

public class ServerGame extends Game {

	public ServerGame(@NonNull String userId) {
		super(userId);
	}
	
	public boolean loadNetworkGame() {
		GameConfiguration xmlGame = gameFromXML(GameMode.NETWORK);
        if(xmlGame!=null) {
        	context.setGameName(xmlGame.getName());
        	try {
				if(xmlGame.configure(context)) {
					return true;
				} else {
					return false;
				}
			} catch (InvalidGameException e) {
				gameInterface.println(context.getUserId(), MessageType.PRIVATE, "Game improperly configured, please try again.");
	            return false;
			}
        }
		return true;
	}
}
