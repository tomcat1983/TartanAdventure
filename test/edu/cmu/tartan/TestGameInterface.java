package edu.cmu.tartan;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

	@BeforeEach
	void testMakeCommanderAndGameInterfaceInitialize() {
		gameInterface.initialize();
		gameInterface = GameInterface.getInterface();
		commander = new Commander();
	}

	@Test
	public void testCommandGet() {
		System.out.println("testCommandGet");

		commander.add("TestCommand");
		commander.apply();

		String command = gameInterface.getCommand();

		assertTrue(command.equals("TestCommand"));
	}

	@Test
	public void testInterfaceReset() {
		System.out.println("testInterfaceReset");

		commander.add("FirstTestCommand");
		commander.apply();

		commander = new Commander();
		commander.add("SecondTestCommand");
		commander.apply();

		gameInterface.resetInterface();

		String command = gameInterface.getCommand();

		assertTrue(command.equals("SecondTestCommand"));
	}

	@Test
	public void testMessagePrint() {
		System.out.println("testMessagePrint");

		gameInterface.print("Message");
		gameInterface.println("PrintLine");
	}
}
