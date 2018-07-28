package edu.cmu.tartan.server;

import edu.cmu.tartan.*;
import edu.cmu.tartan.manager.*;
import edu.cmu.tartan.socket.*;

public class Server {

	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

	public Server(String ip, String port) {
	}
	
	public boolean start() {
		gameInterface.info("Run server");

		IQueueHandler messageQueue = new MessageQueue();
		
		ISocketHandler socketServer = new SocketServer(messageQueue);
		Thread socketServerThread = new Thread((Runnable)socketServer);
		socketServerThread.start();
		
		TartanGameManager tartanGameManager = new TartanGameManager(socketServer, messageQueue);
		Thread gameManagerThread = new Thread(tartanGameManager);
		gameManagerThread.start();
		
		return true;
	}
}
