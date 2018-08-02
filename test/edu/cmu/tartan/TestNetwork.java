package edu.cmu.tartan;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.db.DbAccessor;
import edu.cmu.tartan.manager.IQueueHandler;
import edu.cmu.tartan.manager.MessageQueue;
import edu.cmu.tartan.manager.TartanGameManager;
import edu.cmu.tartan.manager.TartanGameManagerClient;
import edu.cmu.tartan.socket.DesignerSocketServer;
import edu.cmu.tartan.socket.ISocketHandler;
import edu.cmu.tartan.socket.SocketServer;
import edu.cmu.tartan.xml.XmlLoginRole;

public class TestNetwork {
	
	IQueueHandler messageQueue;
	ISocketHandler socketServer;
	ISocketHandler designerSocketServer;
	TartanGameManager tartanGameManager;
	
	TartanGameManagerClient gameManager;
	
	public TestNetwork() {
		beforeTest();
	}
	
	public void beforeTest() {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gameManager = new TartanGameManagerClient(false);
		Thread gameManagerClientThread = new Thread(gameManager);
		gameManagerClientThread.start();
	}
	
	@Test
	public void testLogin() {
		
		boolean returnValue = false;
		returnValue = gameManager.login("", "developer", "AAAAA000", XmlLoginRole.PLAYER);
		
		assertEquals(true, returnValue);
		
		returnValue = gameManager.startGame("developer");
		assertEquals(true, returnValue);
		
		returnValue = gameManager.updateGameState("developer", "go east");
		assertEquals(true, returnValue);
		
		returnValue = gameManager.endGame("","developer");
		assertEquals(true, returnValue);
	}

	@Disabled
	@Test
	public void testResgiser() {
		
		boolean returnValue = false;
		
		returnValue = gameManager.register("", "test1234", "aaaaA000");
		
		assertEquals(true, returnValue);
		
		DbAccessor db = new DbAccessor();
		db.delete("test1234");
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
	public void testWaitForConnection() {
		boolean returnValue = gameManager.waitForConnection();
		assertEquals(true, returnValue);
	}
	
}
