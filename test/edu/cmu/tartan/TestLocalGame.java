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
	void testStartXMLLocalGame() {
		commander.add("9");
		commander.add("help");
		commander.add("east");
		commander.add("status");
		commander.add("pickup document");
		commander.add("pickup shovel");
		commander.add("north");
		commander.add("north");
		commander.add("pickup key");
		commander.add("south");
		commander.add("open lock");
		commander.add("west");
		commander.add("west");
		commander.add("pickup torch");
		commander.add("east");
		commander.add("east");
		commander.add("south");
		commander.add("east");
		commander.add("south");
		commander.add("south");
		commander.add("west");
		commander.add("push fridge");
		commander.add("west");
		commander.add("west");
		commander.add("pickup gold");
		commander.add("east");
		commander.add("north");
		commander.add("pickup food");
		commander.add("south");
		commander.add("east");
		commander.add("east");
		commander.add("put food in microwave");
		commander.add("use microwave");
		commander.add("remove diamond from microwave");
		commander.add("east");
		commander.add("quit");
		commander.add("no");
		commander.apply();
	}
}