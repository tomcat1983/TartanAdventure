package edu.cmu.tartan;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.test.Commander;
import edu.cmu.tartan.xml.GameMode;

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
		game.configureGame(GameMode.LOCAL);
		game.start();	
	}
		
	@Test
	void testStartXMLLocalGame() {
		commander.add("help");
		commander.add("go east");
		commander.add("status");
		commander.add("pickup document");
		commander.add("pickup shovel");
		commander.add("go north");
		commander.add("go north");
		commander.add("pickup key");
		commander.add("go south");
		commander.add("put key in lock");
		commander.add("open lock");
		commander.add("go west");
		commander.add("go west");
		commander.add("pickup torch");
		commander.add("go east");
		commander.add("go east");
		commander.add("go south");
		commander.add("go east");
		commander.add("go south");
		commander.add("go south");
		commander.add("go west");
		commander.add("push fridge");
		commander.add("go west");
		commander.add("go west");
		commander.add("pickup gold");
		commander.add("go east");
		commander.add("go north");
		commander.add("pickup food");
		commander.add("go south");
		commander.add("go east");
		commander.add("go east");
		commander.add("put food in microwave");
		commander.add("use microwave");
		commander.add("remove diamond from microwave");
		commander.add("go east");
		commander.add("quit");
		commander.add("no");
		commander.apply();
		commander.destory();
	}
}