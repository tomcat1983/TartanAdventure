package edu.cmu.tartan;

import org.eclipse.jdt.annotation.NonNull;

import edu.cmu.tartan.xml.GameMode;

public class ServerGame extends Game {

	public ServerGame(@NonNull String userId) {
		super(userId);
	}
	
	public boolean loadNetworkGame() {
		return configureGame(GameMode.NETWORK);
	}
}
