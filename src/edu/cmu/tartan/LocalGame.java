package edu.cmu.tartan;

import org.eclipse.jdt.annotation.NonNull;

import edu.cmu.tartan.manager.IGameControlMessage;

public class LocalGame extends Game implements IGameControlMessage {

	public LocalGame(@NonNull String userId) {
		super(userId);
	}
	
	@Override
	public boolean controlGame(String message) {
		return false;
	}

}
