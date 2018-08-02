package edu.cmu.tartan.server;

import edu.cmu.tartan.GameInterface;
import edu.cmu.tartan.manager.IQueueHandler;
import edu.cmu.tartan.manager.MessageQueue;
import edu.cmu.tartan.manager.TartanGameManager;
import edu.cmu.tartan.socket.DesignerSocketServer;
import edu.cmu.tartan.socket.ISocketHandler;
import edu.cmu.tartan.socket.SocketServer;

public class Server {

	/**
	 * Game interface for game message
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

	/**
	 * Sever interface instance
	 */
	private ServerInterface serverInterface;

	/**
	 *
	 */
	public Server() {
		serverInterface = new ServerInterface();
	}

	/**
	 * @return
	 */
	public boolean start() {
		gameInterface.println("Run server");

		IQueueHandler messageQueue = new MessageQueue();

		ISocketHandler socketServer = new SocketServer(messageQueue);
		Thread socketServerThread = new Thread((Runnable)socketServer);
		socketServerThread.start();

		ISocketHandler designerSocketServer = new DesignerSocketServer(messageQueue);
		Thread designerSocketServerThread = new Thread((Runnable)designerSocketServer);
		designerSocketServerThread.start();

		TartanGameManager tartanGameManager = new TartanGameManager(socketServer, designerSocketServer, messageQueue);
		gameInterface.setGameManager(tartanGameManager);

		Thread gameManagerThread = new Thread(tartanGameManager);
		gameManagerThread.start();

		boolean running = true;

		do {
			ServerInterface.Command command = serverInterface.getCommand();

			switch (command) {
			case EXIT:
				tartanGameManager.endGame("threadName","quit");
				gameManagerThread.interrupt();
//				socketServer.stopSocket();
				tartanGameManager.stopGameManager();
				running = false;
				break;
			default:
				gameInterface.println("Unknown command");
				running = false;
				break;
			}
		} while (running);

		return true;
	}
}
