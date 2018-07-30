package edu.cmu.tartan;

import org.eclipse.jdt.annotation.NonNull;

import edu.cmu.tartan.GameInterface.MessageType;
import edu.cmu.tartan.games.InvalidGameException;
import edu.cmu.tartan.manager.IGameControlMessage;
import edu.cmu.tartan.manager.TartanGameManager;
import edu.cmu.tartan.xml.GameMode;

public class ServerGame extends Game implements IGameControlMessage {

	private TartanGameManager gameManager;

	public ServerGame(@NonNull TartanGameManager gameManager,@NonNull String userId) {
		super(userId);
		this.gameManager = gameManager;
	}
	
	@Override
	public boolean controlGame(String message) {
		gameManager.achievedGoal(context.getUserId());
		return false;
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
