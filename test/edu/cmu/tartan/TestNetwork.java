package edu.cmu.tartan;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.manager.IQueueHandler;
import edu.cmu.tartan.manager.MessageQueue;
import edu.cmu.tartan.manager.TartanGameManager;
import edu.cmu.tartan.manager.TartanGameManagerClient;
import edu.cmu.tartan.socket.DesignerSocketServer;
import edu.cmu.tartan.socket.ISocketHandler;
import edu.cmu.tartan.socket.SocketServer;
import edu.cmu.tartan.xml.XmlLoginRole;

public class TestNetwork {

	static IQueueHandler messageQueue;
	static ISocketHandler socketServer;
	static ISocketHandler designerSocketServer;
	static TartanGameManager tartanGameManager;

	static TartanGameManagerClient gameManager;
	static TartanGameManagerClient designerManager;
	
	@BeforeAll
	public static void beforeTest() {
		messageQueue = new MessageQueue();
		
		socketServer = new SocketServer(messageQueue);
		Thread socketServerThread = new Thread((Runnable)socketServer);
		socketServerThread.start();

		designerSocketServer = new DesignerSocketServer(messageQueue);
		Thread designerSocketServerThread = new Thread((Runnable)designerSocketServer);
		designerSocketServerThread.start();

		tartanGameManager = new TartanGameManager(socketServer, designerSocketServer, messageQueue);

		Thread gameManagerThread = new Thread(tartanGameManager);
		gameManagerThread.start();

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		gameManager = new TartanGameManagerClient(false);
		Thread gameManagerClientThread = new Thread(gameManager);
		gameManagerClientThread.start();
		
		designerManager = new TartanGameManagerClient(true);
		Thread designerClientThread = new Thread(designerManager);
		designerClientThread.start();
	}
	
	@AfterAll
	public static void afterTest() {
		messageQueue = null;
		socketServer = null;
		designerSocketServer = null;
		tartanGameManager = null;

		gameManager = null;
		designerManager = null;
	}

	@Test
	public void testLogin() {
		
		String userId = "developer";
		
		boolean returnValue = false;
		returnValue = gameManager.waitForConnection();
		assertEquals(true, returnValue);
		
		returnValue = gameManager.login("", userId, "AAAAA000", XmlLoginRole.PLAYER);
		assertEquals(true, returnValue);
		
		returnValue = gameManager.startGame(userId);
		assertEquals(true, returnValue);
		
		returnValue = gameManager.updateGameState(userId, "go east");
		assertEquals(true, returnValue);
		
		returnValue = tartanGameManager.sendToClient(userId, "go east");
		assertEquals(true, returnValue);

		returnValue = tartanGameManager.sendToAll(userId, "Hello2");
		assertEquals(true, returnValue);
		
		returnValue = tartanGameManager.sendToOthers(userId, "Hello3");
		assertEquals(false, returnValue);
		
		Runnable endGame = new Runnable() {
			public void run() {
				gameManager.endGame("", userId);
			}
		};
		
		Thread endGameThread = new Thread(endGame);
		endGameThread.start();
		
		returnValue = tartanGameManager.loseTheGame(userId, "LOSE");
		assertEquals(true, returnValue);
	}

	@Disabled
	@Test
	public void testResgiser() {

		boolean returnValue = false;

		returnValue = gameManager.register("", "test1234", "aaaaA000");

		assertEquals(true, returnValue);
	}

	@Test
	public void testShouldReturnTrueWhenInputValideUserId() {
		String userId = "test1234";
		boolean returnValue = gameManager.validateUserId(userId);
		assertEquals(true, returnValue);
	}

	@Test
	public void testShouldReturnFalseWhenInputInvalideUserId() {
		String userId = "test";
		boolean returnValue = gameManager.validateUserId(userId);
		assertEquals(false, returnValue);
	}

	@Test
	public void testShouldReturnTrueWhenInputValideUserPw() {
		String userId = "aaaaA000";
		boolean returnValue = gameManager.validateUserPw(userId);
		assertEquals(true, returnValue);
	}

	@Test
	public void testShouldReturnFalseWhenInputInvalideUserPw() {
		String userId = "aaaa";
		boolean returnValue = gameManager.validateUserPw(userId);
		assertEquals(false, returnValue);
	}

	@Test
	public void testEncryptionPassword() {
		String encryptionPw = "fa876f642212ea67a08f0a56fbcf672e4e2c1f51a2d7d6aba6ee84a8bfca2340";
		String password = "AAAAA000";
		String returnValue = gameManager.encryptPassword(password);
		assertEquals(encryptionPw, returnValue);
	}

	@Test
	public void testLoginDesignerMode() {
		String userId = "designer";
		
		boolean returnValue = false;
		returnValue = designerManager.waitForConnection();
		assertEquals(true, returnValue);
		
		returnValue = designerManager.login("", userId, "AAAAA000", XmlLoginRole.DESIGNER);
		assertEquals(true, returnValue);
	}

}
