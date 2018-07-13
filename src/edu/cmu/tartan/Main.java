package edu.cmu.tartan;

/**
 * A simple driver for the game
 */
public class Main {
	public static void main(String[] args) {
		Game game = new Game();
		if(game.configureGame()) {
			game.start();			
		}
	}
}
