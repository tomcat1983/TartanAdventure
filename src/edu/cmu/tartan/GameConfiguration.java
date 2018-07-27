package edu.cmu.tartan;

import edu.cmu.tartan.games.InvalidGameException;

/**
 * The abstract class that all games must extend. Essentially all games must provide a configuration
 */
public abstract class GameConfiguration {

    /**
     * The name of this game
     */
    private String name;
    
	/**
	 * Constructor
	 * @param game name
	 */
	public GameConfiguration(String name) {
		this.name = name;
	}
	
	/** 
	 * Get the game name
	 * @return game name
	 */
	public String getName() {
		return name;
	}
	
    /**
     * Configure the game
     * @param game the Game object that will manage execution
     * @throws InvalidGameException indicates configuration error
     * @see Game
     */
    public abstract boolean  configure(GameContext context) throws InvalidGameException;

}


