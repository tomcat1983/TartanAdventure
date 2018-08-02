package edu.cmu.tartan.manager;

import edu.cmu.tartan.ServerGame;

public class TartanGameThread implements Runnable {

	private ServerGame serverGame;

	/**
	 * @param userId
	 */
	public TartanGameThread(String userId) {
		serverGame = new ServerGame(userId);
	}

	@Override
	public void run() {
		serverGame.loadNetworkGame();
		serverGame.start();
	}
}
