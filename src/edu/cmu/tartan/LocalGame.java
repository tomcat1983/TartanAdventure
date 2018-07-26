package edu.cmu.tartan;

import edu.cmu.tartan.manager.IGameControlMessage;

public class LocalGame extends Game implements IGameControlMessage {

	public LocalGame() {
		super();
	}
	
	@Override
	public boolean controlGame(String message) {
		return false;
	}

}
