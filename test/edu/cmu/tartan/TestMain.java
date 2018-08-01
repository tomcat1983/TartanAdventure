package edu.cmu.tartan;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.test.Commander;

public class TestMain {

	/**
	 * Game interface for game message
	 */
	protected GameInterface gameInterface = GameInterface.getInterface();

	/**
	 * Commander for user input
	 */
	private Commander commander;

	/**
	 * Arguments for main processor
	 */
	String[] args;

	@BeforeEach
	void testMakeCommander() {
		commander = new Commander();
	}

	@AfterEach
	void testRunClient() {
	}

	@Test
	public void testServerMode() {
		System.out.println("testServerMode");

		commander.add("exit");
		commander.apply();

		String propertiesUri = System.getProperty("user.dir") + File.separator
				+ "resources" + File.separator + "server";

		args = new String[1];
		args[0] = propertiesUri;

		Main.main(args);

		commander.destory();

		assertEquals("Run server", commander.getResultNextLine());

		// newline
		assertEquals("", commander.getResultNextLine());
		// prompt
		assertEquals("> ", commander.getResultNextLine());
	}

	@Test
	public void testServerModeWithInvalidCommand() {
		System.out.println("testServerModeWithInvalidCommand");

		commander.add("invalidCmd");
		commander.add("exit");
		commander.apply();

		String propertiesUri = System.getProperty("user.dir") + File.separator
				+ "resources" + File.separator + "server";

		args = new String[1];
		args[0] = propertiesUri;

		Main.main(args);

		commander.destory();

		assertEquals("Run server", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());

		// prompt + message
		assertEquals("> Invalid command : invalidCmd", commander.getResultNextLine());
	}

	@Test
	public void testClientMode() {
		System.out.println("testClientMode");

		commander.add("exit");
		commander.apply();

		String propertiesUri = System.getProperty("user.dir") + File.separator
				+ "resources" + File.separator + "client";

		args = new String[1];
		args[0] = propertiesUri;

		Main.main(args);

		commander.destory();

		assertEquals("[Tartan Adventure]", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("Room Escape", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("Select the game mode", commander.getResultNextLine());
		assertEquals("> 1       Local mode", commander.getResultNextLine());
		assertEquals("> 2       Network mode", commander.getResultNextLine());
		assertEquals("> help    What are local mode and network mode?", commander.getResultNextLine());
		assertEquals("> exit    The game will be terminated.", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("Choose the mode", commander.getResultNextLine());
		assertEquals("> ", commander.getResultNextLine());
	}

	@Test
	public void testClientModeWithInvalidCommand() {
		System.out.println("testClientModeWithInvalidCommand");

		commander.add("invalidCmd");
		commander.add("exit");
		commander.apply();

		String propertiesUri = System.getProperty("user.dir") + File.separator
				+ "resources" + File.separator + "client";

		args = new String[1];
		args[0] = propertiesUri;

		Main.main(args);

		commander.destory();

		assertEquals("[Tartan Adventure]", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("Room Escape", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("Select the game mode", commander.getResultNextLine());
		assertEquals("> 1       Local mode", commander.getResultNextLine());
		assertEquals("> 2       Network mode", commander.getResultNextLine());
		assertEquals("> help    What are local mode and network mode?", commander.getResultNextLine());
		assertEquals("> exit    The game will be terminated.", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("Choose the mode", commander.getResultNextLine());

		// prompt + message
		assertEquals("> Invalid command : invalidCmd", commander.getResultNextLine());

		assertEquals("", commander.getResultNextLine());
		assertEquals("Choose the mode", commander.getResultNextLine());
		assertEquals("> ", commander.getResultNextLine());
	}

	@Test
	public void testNoArgs() {
		System.out.println("testNoArgs");

		commander.add("exit");
		commander.apply();

		args = new String[0];

		Main.main(args);

		commander.destory();

		assertEquals("[Tartan Adventure]", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("Room Escape", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("Select the game mode", commander.getResultNextLine());
		assertEquals("> 1       Local mode", commander.getResultNextLine());
		assertEquals("> 2       Network mode", commander.getResultNextLine());
		assertEquals("> help    What are local mode and network mode?", commander.getResultNextLine());
		assertEquals("> exit    The game will be terminated.", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("Choose the mode", commander.getResultNextLine());
	}

	@Test
	public void testNoArgsWithInvaildCommand() {
		System.out.println("testNoArgsWithNoArgs");

		commander.add("invalidCmd");
		commander.add("exit");
		commander.apply();

		args = new String[0];

		Main.main(args);

		commander.destory();

		assertEquals("[Tartan Adventure]", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("Room Escape", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("Select the game mode", commander.getResultNextLine());
		assertEquals("> 1       Local mode", commander.getResultNextLine());
		assertEquals("> 2       Network mode", commander.getResultNextLine());
		assertEquals("> help    What are local mode and network mode?", commander.getResultNextLine());
		assertEquals("> exit    The game will be terminated.", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("Choose the mode", commander.getResultNextLine());

		// prompt + message
		assertEquals("> Invalid command : invalidCmd", commander.getResultNextLine());

		assertEquals("", commander.getResultNextLine());
		assertEquals("Choose the mode", commander.getResultNextLine());
		assertEquals("> ", commander.getResultNextLine());
	}

	@Test
	public void testInvalidArgs() {
		System.out.println("testInvalidArgs");

		commander.add("exit");
		commander.apply();

		String propertiesUri = System.getProperty("user.dir") + File.separator
				+ "resources";

		args = new String[1];
		args[0] = propertiesUri;

		Main.main(args);

		commander.destory();

		// No message
		assertEquals("Fail to read setting file. Can't run!!", commander.getResultNextLine());
	}

	@Test
	public void testInvalidArgsWithNoArgs() {
		System.out.println("testInvalidArgsWithNoArgs");

		commander.add("invalidCmd");
		commander.add("exit");
		commander.apply();

		String propertiesUri = System.getProperty("user.dir") + File.separator
				+ "resources";

		args = new String[1];
		args[0] = propertiesUri;

		Main.main(args);

		commander.destory();

		// No message
		assertEquals("Fail to read setting file. Can't run!!", commander.getResultNextLine());
	}
}
