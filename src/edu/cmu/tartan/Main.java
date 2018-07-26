package edu.cmu.tartan;

/**
 * A simple driver for the game
 */
public class Main {
	public static void main(String[] args) {
		LocalGame localGame = new LocalGame();
		if(localGame.configureGame()) {
			localGame.start();			
		}
	}
}