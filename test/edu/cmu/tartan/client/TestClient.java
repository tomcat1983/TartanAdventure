package edu.cmu.tartan.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import edu.cmu.tartan.test.Commander;

class TestClient {

	Commander commander = null;
	
	@BeforeEach
	void testMakeCommander() {
		commander = new Commander();
	}
	
	@AfterEach
	void testRunClient() {		
		Client client = new Client("localhost", "8088");
		client.start();
	}

	@Test
	void testWhenInputHelp() {
		commander.add("help");
		commander.add("1");
		commander.add("new");
		commander.add("1");
		commander.add("quit");
		commander.apply();
	}

	@Test
	void testWhenInputInvalidRunningMode() {
		commander.add("55");
		commander.add("1");
		commander.add("new");
		commander.add("1");
		commander.add("quit");
		commander.apply();		
	}

	@Test
	void testWhenSelectLocalGameAndNewGame() {
		commander.add("1");
		commander.add("new");
		commander.add("1");
		commander.add("quit");
		commander.apply();		
	}

	@Test
	void testWhenSelectLocalGameAndContinueGame() {
		commander.add("1");
		commander.add("continue");
		commander.apply();
	}

	@Test
	void testWhenSelectNetworkGameMode() {
		commander.add("2");
		commander.apply();
	}

	@Test
	void testWhenSelectDesignerMode() {
		commander.add("99");
		commander.apply();
	}

}
