package edu.cmu.tartan;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.cmu.tartan.test.Commander;
import edu.cmu.tartan.xml.GameMode;

class TestSaveAndLoad {

	Commander commander;
	LocalGame game;
	public static final String TEST_FILE_NAME = "test_save_load.dat";

	public void deleteFile(String fileLocation) {
		File file = new File(fileLocation);
		if (file.exists()) {
			file.delete();
		}
	}

	
	@BeforeEach
	void testMakeCommander() {
		deleteFile(TEST_FILE_NAME);
	}
	
	@Test
	void testLoadWhenNoSaveFile() {
		game = new LocalGame(Player.DEFAULT_USER_NAME);
		game.configureGame(GameMode.LOCAL);
		assertFalse(game.loadAndStart(Player.DEFAULT_USER_NAME, TEST_FILE_NAME));
	}

	@Test
	void testLoadWhenExistSaveFile() {
		game = new LocalGame(Player.DEFAULT_USER_NAME);
		game.configureGame(GameMode.LOCAL);
		assertFalse(game.save(TEST_FILE_NAME, TEST_FILE_NAME));
		assertTrue(game.save(Player.DEFAULT_USER_NAME, TEST_FILE_NAME));
		
		commander = new Commander();
		commander.add("quit");
		commander.add("no");
		commander.apply();

		assertTrue(game.loadAndStart(Player.DEFAULT_USER_NAME, TEST_FILE_NAME));
		commander.destory();
		deleteFile(TEST_FILE_NAME);		
	}
}
