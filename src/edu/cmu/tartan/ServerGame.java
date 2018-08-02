package edu.cmu.tartan;

import org.eclipse.jdt.annotation.NonNull;

import edu.cmu.tartan.xml.GameMode;

public class ServerGame extends Game {

	/**
	 * @param userId
	 */
	public ServerGame(@NonNull String userId) {
		super(userId);
	}

	/**
	 * @return
	 */
	public boolean loadNetworkGame() {
		return configureGame(GameMode.NETWORK);
	}
}
