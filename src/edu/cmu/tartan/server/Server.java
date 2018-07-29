package edu.cmu.tartan.server;

import java.util.logging.Logger;

import edu.cmu.tartan.manager.*;
import edu.cmu.tartan.socket.*;

public class Server {

	/**
	 * Game logger for game log
	 */
	private Logger gameLogger = Logger.getGlobal();
	
	/**
	 * Server port
	 */
	int port;

	public Server() {
	}
	
	public boolean start() {
		gameLogger.info("Run server");

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
