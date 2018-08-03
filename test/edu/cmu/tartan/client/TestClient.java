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
		System.out.println("testWhenInputHelp");

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

	@Disabled
	@Test
	void testWhenInputInvalidRunningMode() {
		System.out.println("testWhenInputInvalidRunningMode");

		commander.add("55");
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
		// > 55
		assertEquals("> Invalid command : 55", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("Choose the mode", commander.getResultNextLine());
	}

	@Disabled
	@Test
	void testWhenSelectLocalGameAndNewGame() {
		System.out.println("testWhenSelectLocalGameAndNewGame");

		commander.add("1");
		commander.add("new");
		commander.add("quit");
		commander.add("no");
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
		// > 1
		assertEquals("> [Local mode]", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("Let's play :", commander.getResultNextLine());
		assertEquals("> continue    Play the latest saved game continuously", commander.getResultNextLine());
		assertEquals("> new         Play a new game.", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("Please select the game play mode.", commander.getResultNextLine());
		// > new
		assertEquals("> Welcome to Tartan Adventure (1.0), by Tartan Inc..", commander.getResultNextLine());
		assertEquals("Game: This is [Collect Item] [Explore Room] [Get Point] Game", commander.getResultNextLine());
		assertEquals("To get help type 'help' ... let's begin", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("Enjoy your game!", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("You are in Room1.", commander.getResultNextLine());
		assertEquals("You can go east to Room2. ", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("> ", commander.getResultNextLine());
		// > quit
		assertEquals("You collected 0 out of 2 items.", commander.getResultNextLine());
		assertEquals("You have explored 0 out of 4 rooms.", commander.getResultNextLine());
		assertEquals("You scored 0 out of 100 points.", commander.getResultNextLine());
		assertEquals("Will you save the current state?(yes/no)", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		// > no
		assertEquals("> Save is rejected.", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
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

	@Disabled
	@Test
	void testWhenSelectLocalGameAndContinueGameWithoutSavedGame() {
		System.out.println("testWhenSelectLocalGameAndContinueGameWithoutSavedGame");

		commander.add("1");
		commander.add("continue");
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
		// > 1
		assertEquals("> [Local mode]", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("Let's play :", commander.getResultNextLine());
		assertEquals("> continue    Play the latest saved game continuously", commander.getResultNextLine());
		assertEquals("> new         Play a new game.", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
		assertEquals("Please select the game play mode.", commander.getResultNextLine());
		// > continue
		assertEquals("> There is no saved game.", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
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

	@Disabled
	@Test
	void testWhenSelectNetworkGameModeWithoutNetwork() {
		System.out.println("testWhenSelectNetworkGameModeWithoutNetwork");

		commander.add("2");
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
		// > 2
		assertEquals("> I'm sorry. The game server is busy.", commander.getResultNextLine());
		assertEquals("Please retry to connect later.", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
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

	@Disabled
	@Test
	void testWhenSelectDesignerModeWithoutNetwork() {
		System.out.println("testWhenSelectDesignerModeWithoutNetwork");

		commander.add("99");
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
		// > 99
		assertEquals("> I'm sorry. The game server is busy.", commander.getResultNextLine());
		assertEquals("Please retry to connect later.", commander.getResultNextLine());
		assertEquals("", commander.getResultNextLine());
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

}
