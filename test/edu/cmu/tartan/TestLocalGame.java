package edu.cmu.tartan;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.client.Client;
import edu.cmu.tartan.test.Commander;

public class TestLocalGame {
	Commander commander = null;
	LocalGame game;
	@BeforeEach
	void testMakeCommander() {
		commander = new Commander();
	}
	
	@AfterEach
	void testRunLocalGame() {
		game = new LocalGame(Player.DEFAULT_USER_NAME);
		game.configureGame();
		game.start();	
	}

	@Test
	void testStartLocalGame() {
		commander.add("9");
		commander.add("help");
		commander.add("east");
		commander.add("status");
		commander.add("pickup document");
		commander.add("pickup shovel");
		commander.add("drop shovel");
		commander.add("quit");
		commander.apply();
	}
}