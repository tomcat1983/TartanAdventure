package edu.cmu.tartan.client;

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
	}

	@AfterEach
	void testRunClient() {
		String fileUri = System.getProperty("user.dir") + File.separator + "config.properties";
		new Config(fileUri);

		Client client = new Client();
		client.start();
	}

	@Test
	void testWhenInputHelp() {
		commander.add("help");
		commander.add("exit");
		commander.apply();
	}

	@Test
	void testWhenInputInvalidRunningMode() {
		commander.add("55");
		commander.add("exit");
		commander.apply();
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
	}

	@Test
	void testWhenSelectLocalGameAndContinueGame() {
		commander.add("1");
		commander.add("continue");
		commander.add("quit");
		commander.add("no");
		commander.add("exit");
		commander.apply();
	}

	@Test
	void testWhenSelectNetworkGameMode() {
		commander.add("2");
		commander.add("quit");
		commander.add("exit");
		commander.apply();
	}

	@Disabled
	@Test
	void testWhenSelectDesignerMode() {
		commander.add("99");
		commander.apply();
	}

}
