package edu.cmu.tartan.manager;

import org.junit.jupiter.api.Test;

import edu.cmu.tartan.GameInterface;
import edu.cmu.tartan.socket.DesignerSocketServer;
import edu.cmu.tartan.socket.ISocketHandler;
import edu.cmu.tartan.socket.SocketServer;

class TestGameManager {
	/**
	 * Game interface for game message
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

	@Test
	void testTartanGameManager() {
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

		tartanGameManager.stopGameManager();
	}

	@Test
	void testTartanGameManagerClientWithUser() {
		TartanGameManagerClient gameManager = new TartanGameManagerClient(false);
		Thread gameManagerThread = new Thread(gameManager);
		gameManagerThread.start();

		gameManager.stopGameManager();
	}

	@Test
	void testTartanGameManagerClientWithDesigner() {
		TartanGameManagerClient gameManager = new TartanGameManagerClient(true);
		Thread gameManagerThread = new Thread(gameManager);
		gameManagerThread.start();

		gameManager.stopGameManager();
	}
}
