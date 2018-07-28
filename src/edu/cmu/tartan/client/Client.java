package edu.cmu.tartan.client;

import edu.cmu.tartan.GameInterface;
import edu.cmu.tartan.LocalGame;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.socket.ISocketHandler;
import edu.cmu.tartan.socket.SocketClient;
import edu.cmu.tartan.socket.SocketServer;

public class Client {

	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();
	
	/**
	 * Client interface instance
	 */
	private ClientInterface clientInterface;
	
	/**
	 * Server IP
	 */
	String serverIp;
	
	/**
	 * Server port
	 */
	int serverPort;

	
	public Client(String ip, String port) {
		serverIp = ip;
		serverPort = Integer.parseInt(port);
		
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
		gameInterface.println("TBD");
		return true;
	}
	
	private boolean newGame() {
		LocalGame localGame = new LocalGame(Player.DEFAULT_USER_NAME);
		if(localGame.configureGame()) {
			localGame.start();
		}
		
		return true;
	}

	private boolean runNetworkMode() {
		SocketClient socketClient = new SocketClient(serverIp, serverPort);
		Thread socketClientThread = new Thread((Runnable)socketClient);
		socketClientThread.start();
		
		socketClient.waitToConnection(1000);

		gameInterface.println("Send message : " + Player.DEFAULT_USER_NAME);
		socketClient.sendMessage(Player.DEFAULT_USER_NAME);
		
		return true;
	}
	
	private boolean runDesignerMode() {
		gameInterface.println("TBD");
		return true;
	}
}
