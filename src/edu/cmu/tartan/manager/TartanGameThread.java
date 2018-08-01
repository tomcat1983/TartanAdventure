package edu.cmu.tartan.manager;

import edu.cmu.tartan.ServerGame;

public class TartanGameThread implements Runnable {
	
	private ServerGame serverGame;
	
	public TartanGameThread(String userId) {
		serverGame = new ServerGame(userId);
	}

	@Override
	public void run() {
		System.out.println("Start Game : " + Thread.currentThread().getName());
		serverGame.loadNetworkGame();
		serverGame.start();
	}
}
