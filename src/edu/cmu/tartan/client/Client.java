package edu.cmu.tartan.client;

import edu.cmu.tartan.Game;
import edu.cmu.tartan.GameInterface;

public class Client {

	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();
	
	/**
	 * Client interface instance
	 */
	private ClientInterface clientInterface;

	
	public Client(String ip, String port) {
		gameInterface.info("Run Client : " + ip + ":" + port);
		clientInterface = new ClientInterface();
	}
	
	public boolean start() {
		clientInterface.printGameModeMessage();
		boolean result = false;
		
		switch (clientInterface.getRunningMode()) {
		case LOCAL:
			result = runLocalMode();
			break;
		case NETWORK:
			result = runNetworkMode();
			break;
		case DESIGNER:
			result = runDesignerMode();
			break;
		default:
			gameInterface.severe("Unknown running mode");
			break;
		}
		
		return result;
	}
	
	private boolean runLocalMode() {
		clientInterface.printLocalModeMessage();
		ClientInterface.LocalModeCommand command = clientInterface.getLocalModeCommand();
		boolean result = false;

		switch (command) {
		case CONTINUE:
			result = continueGame();
			break;
		case NEW:
			result = newGame();
			break;
		default:
			gameInterface.severe("Unknow local mode command");
			break;
		}

		return result;
	}
	
	private boolean continueGame() {
		return true;
	}
	
	private boolean newGame() {
		Game game = new Game();

		if(game.configureGame()) {
			game.start();			
		}
		
		return true;
	}

	private boolean runNetworkMode() {
		gameInterface.println("TBD");
		return true;
	}
	
	private boolean runDesignerMode() {
		gameInterface.println("TBD");
		return true;
	}
}
