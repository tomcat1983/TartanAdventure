package edu.cmu.tartan;

public class Server {

	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

	public Server(String ip, String port) {
		gameInterface.info("Run server : " + ip + ":" + port);
		LocalGame localGame = new LocalGame(Player.DEFAULT_USER_NAME);
		if(localGame.configureGame()) {
			localGame.start();
		}
	}
}
