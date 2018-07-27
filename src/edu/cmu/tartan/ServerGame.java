package edu.cmu.tartan;

import edu.cmu.tartan.manager.IGameControlMessage;
import edu.cmu.tartan.manager.TartanGameManager;

public class ServerGame extends Game implements IGameControlMessage {

	private TartanGameManager gameManager;

	public ServerGame(TartanGameManager gameManager, String userId) {
		super(userId);
		this.gameManager = gameManager;
	}
	
	@Override
	public boolean controlGame(String message) {
		gameManager.achievedGoal(userId);
		return false;
	}
}
