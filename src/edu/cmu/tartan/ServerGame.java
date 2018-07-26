package edu.cmu.tartan;

import edu.cmu.tartan.manager.IGameControlMessage;

public class ServerGame extends Game implements IGameControlMessage {

	public ServerGame() {
		super();
	}
	
	@Override
	public boolean controlGame(String message) {
		
		return false;
	}
}
