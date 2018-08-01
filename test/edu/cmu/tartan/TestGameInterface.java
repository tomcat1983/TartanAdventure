package edu.cmu.tartan;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.manager.IQueueHandler;
import edu.cmu.tartan.manager.MessageQueue;
import edu.cmu.tartan.manager.TartanGameManager;
import edu.cmu.tartan.socket.DesignerSocketServer;
import edu.cmu.tartan.socket.ISocketHandler;
import edu.cmu.tartan.socket.SocketServer;
import edu.cmu.tartan.test.Commander;

public class TestGameInterface {

	/**
	 * Game interface for game message
	 */
	protected GameInterface gameInterface = GameInterface.getInterface();

	/**
	 * Commander for user input
	 */
	private Commander commander;

	/**
	 * Test user id
	 */
	private static final String userId = "UserID";

	@BeforeEach
	void testMakeCommanderAndGameInterfaceInitialize() {
		commander = new Commander();
	}

	@Test
	public void testCommandGet() {
		System.out.println("testCommandGet");

		commander.add("TestCommand");
		commander.apply();
		commander.destory();

		String command = gameInterface.getCommand(userId);

		assertEquals("TestCommand", command);
	}

	@Test
	public void testCommandGetUsingLocalId() {
		System.out.println("testCommandGetUsingLocalId");

		commander.add("TestCommand");
		commander.apply();
		commander.destory();

		String command = gameInterface.getCommand(GameInterface.USER_ID_LOCAL_USER);

		assertEquals("TestCommand", command);
	}

	@Test
	public void testInterfaceReset() {
		System.out.println("testInterfaceReset");

		commander.add("FirstTestCommand");
		commander.apply();
		commander.destory();

		commander = new Commander();
		commander.add("SecondTestCommand");
		commander.apply();
		commander.destory();

		gameInterface.resetInterface();

		String command = gameInterface.getCommand(userId);
		System.out.println(command);

		assertEquals("SecondTestCommand", command);
	}

	@Test
	public void testInterfaceResetUsingLocalId() {
		System.out.println("testInterfaceResetUsingLocalId");

		commander.add("FirstTestCommand");
		commander.apply();
		commander.destory();

		commander = new Commander();
		commander.add("SecondTestCommand");
		commander.apply();
		commander.destory();

		gameInterface.resetInterface();

		String command = gameInterface.getCommand(GameInterface.USER_ID_LOCAL_USER);

		assertEquals("SecondTestCommand", command);
	}

	@Test
	public void testMessagePrint() {
		System.out.println("testMessagePrint");

		commander.apply();

		gameInterface.print("Message");

		commander.destory();

		assertEquals("Message", commander.getResultNextLine());
	}

	@Test
	public void testMessagePrintln() {
		System.out.println("testMessagePrintln");

		commander.apply();

		gameInterface.println("Message");

		commander.destory();

		assertEquals("Message", commander.getResultNextLine());
	}

	@Test
	public void testCommandGetwithGameManager() {
		System.out.println("testCommandGetwithGameManager");

		commander.add("System Input");
		commander.apply();

		IQueueHandler messageQueue = new MessageQueue();
		ISocketHandler socketServer = new SocketServer(messageQueue);
		ISocketHandler designerSocketServer = new DesignerSocketServer(messageQueue);
		TartanGameManager manager = new TartanGameManager(socketServer, designerSocketServer, messageQueue);

		gameInterface.setGameManager(manager);

		gameInterface.putCommand(userId, "Input with userId");
		gameInterface.putCommand(GameInterface.USER_ID_LOCAL_USER, "Input with local id");

		String command = gameInterface.getCommand(userId);

		commander.destory();

		assertEquals("Input with userId", command);
	}

	@Test
	public void testCommandGetwithGameManagerAndPutUsingLocalId() {
		System.out.println("testCommandGetwithGameManager");

		commander.add("System Input");
		commander.apply();

		IQueueHandler messageQueue = new MessageQueue();
		ISocketHandler socketServer = new SocketServer(messageQueue);
		ISocketHandler designerSocketServer = new DesignerSocketServer(messageQueue);
		TartanGameManager manager = new TartanGameManager(socketServer, designerSocketServer, messageQueue);

		gameInterface.setGameManager(manager);

		gameInterface.putCommand(userId, "Input with userId");
		gameInterface.putCommand(GameInterface.USER_ID_LOCAL_USER, "Input with local id");

		String command1 = gameInterface.getCommand(GameInterface.USER_ID_LOCAL_USER);
		String command2 = gameInterface.getCommand(GameInterface.USER_ID_LOCAL_USER);

		commander.destory();

		assertEquals("Input with local id", command1);
		assertEquals("System Input", command2);
	}
}
