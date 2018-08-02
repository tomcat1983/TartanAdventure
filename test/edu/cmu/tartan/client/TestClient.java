package edu.cmu.tartan.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.config.Config;
import edu.cmu.tartan.test.Commander;

class TestClient {

	Commander commander = null;

	@BeforeEach
	void testMakeCommander() {
		commander = new Commander();

		String fileUri = System.getProperty("user.dir") + File.separator + "config.properties";
		Config config = new Config(fileUri);
		config.readPropertyFile();
	}

	@AfterEach
	void testRunClient() {
	}

	@Test
	void testWhenInputHelp() {
		commander.add("help");
		commander.add("exit");
		commander.apply();

		Client client = new Client();
		client.start();

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
		// > help
		assertEquals("> In local mode, you can enjoy the game without internet connection.", commander.getResultNextLine());
		assertEquals("You can save the game state during the game and load the game states saved latest.", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("In network mode, you can enjoy the game with other players at the same time through internet connection.", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("Choose the mode", commander.getResultNextLine());
		assertEquals("> ", commander.getResultNextLine());
	}

	@Test
	void testWhenInputInvalidRunningMode() {
		commander.add("55");
		commander.add("exit");
		commander.apply();
		commander.destory();

		Client client = new Client();
		client.start();
	}

	@Test
	void testWhenSelectLocalGameAndNewGame() {
		commander.add("1");
		commander.add("new");
		commander.add("8");
		commander.add("quit");
		commander.add("no");
		commander.add("exit");
		commander.apply();
		commander.destory();

		Client client = new Client();
		client.start();
	}

	@Test
	void testWhenSelectLocalGameAndContinueGame() {
		commander.add("1");
		commander.add("continue");
		commander.add("quit");
		commander.add("no");
		commander.add("exit");
		commander.apply();
		commander.destory();

		Client client = new Client();
		client.start();
	}

	@Test
	void testWhenSelectNetworkGameMode() {
		commander.add("2");
		commander.add("quit");
		commander.add("exit");
		commander.apply();
		commander.destory();

		Client client = new Client();
		client.start();
	}

	@Disabled
	@Test
	void testWhenSelectDesignerMode() {
		commander.add("99");
		commander.apply();
		commander.destory();

		Client client = new Client();
		client.start();
	}

}
