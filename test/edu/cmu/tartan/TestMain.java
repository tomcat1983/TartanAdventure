package edu.cmu.tartan;

import org.junit.jupiter.api.Test;

import edu.cmu.tartan.test.Commander;

public class TestMain {

	@Test
	public void test() {
		Commander commander = new Commander();
		
		commander.add("1");
		commander.add("quit");
		
		commander.apply();
	    
		LocalGame game = new LocalGame(Player.DEFAULT_USER_NAME);
		if(game.configureGame()) {
			game.start();			
		}
	}

}
