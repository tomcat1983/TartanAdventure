package edu.cmu.tartan.client;

import edu.cmu.tartan.GameInterface;
import edu.cmu.tartan.LocalGame;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.socket.SocketClient;

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
	
	/**
	 * Socket to server
	 */
	SocketClient socket;
	
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
			gameInterface.println("Unknown running mode");
			break;
		}
		
		return result;
	}
	
	private boolean runLocalMode() {
		clientInterface.printLocalModeMessage();
		ClientInterface.LocalModeCommand command = clientInterface.getLocalModeCommand();

		switch (command) {
		case CONTINUE:
			return continueGame();
		case NEW:
			return newGame();
		default:
			gameInterface.println("Unknow local mode command");
			break;
		}

		return false;
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
	
	private boolean connectServer(int timeout) {
		socket = new SocketClient(serverIp, serverPort);
		Thread socketClientThread = new Thread((Runnable)socket);
		socketClientThread.start();
		
		return socket.waitToConnection(timeout);
	}
	
	private boolean sendMessage(String message) {
		return socket.sendMessage(message);
	}
	
	private boolean runNetworkMode() {
		if (connectServer(1000)) {
			gameInterface.println("Send message : " + Player.DEFAULT_USER_NAME);
			sendMessage(Player.DEFAULT_USER_NAME);
			return true;
		} else {
			gameInterface.println("Connetion fail");
		}
		
		return false;
	}
	
	private boolean runDesignerMode() {
		if (connectServer(1000)) {
			gameInterface.println("Send message : " + Player.DEFAULT_USER_NAME);
			sendMessage(Player.DEFAULT_USER_NAME);
			return true;
		} else {
			gameInterface.println("Connetion fail");
		}

		return true;
	}
}
