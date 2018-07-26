package edu.cmu.tartan;

public class Server {

	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

	public Server(String ip, String port) {
		gameInterface.info("Run server : " + ip + ":" + port);
		Game game = new Game();

		if(game.configureGame()) {
			game.start();			
		}
	}
}
