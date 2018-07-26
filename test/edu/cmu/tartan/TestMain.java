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
	    
		Game game = new Game();
		if(game.configureGame()) {
			game.start();			
		}
	}

}
