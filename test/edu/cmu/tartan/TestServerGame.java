package edu.cmu.tartan;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestServerGame {

	@Test
	void testWhenServerLoadMap() {
		ServerGame game = new ServerGame(Player.DEFAULT_USER_NAME);
		assertTrue(game.loadNetworkGame());
	}

}
