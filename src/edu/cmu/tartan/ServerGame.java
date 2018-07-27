package edu.cmu.tartan;

import org.eclipse.jdt.annotation.NonNull;

import edu.cmu.tartan.manager.IGameControlMessage;
import edu.cmu.tartan.manager.TartanGameManager;

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
}
