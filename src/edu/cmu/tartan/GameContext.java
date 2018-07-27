package edu.cmu.tartan;

import java.io.Serializable;
import java.util.ArrayList;

import edu.cmu.tartan.goal.GameGoal;

public class GameContext implements Serializable {
    /**
	 * serial version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
     * The set of goals for a game
     */
    private ArrayList<GameGoal> goals = new ArrayList<>();
	/**
     * The player for the game
     */
    private Player player;
	/**
     * The name and description of the active game
     */
    private String gameName = "";
    private String gameDescription = "";
    /**
     * The userId is user name with local game. But if game is network mode, user name set userId.
     */
    private String userId;
    
    public GameContext(String userId) {
    	this.userId = userId;
    }
    /**
	 * @return the goals
	 */
	public ArrayList<GameGoal> getGoals() {
		return goals;
	}
	/**
	 * @param goals the goals to set
	 */
	public void addGoal(GameGoal goals) {
		this.goals.add(goals);
	}
    /**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
    /**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
    /**
     * Set player game goal
     */
    public void setPlayerGameGoal() {
        for (GameGoal g : goals) {
            this.player.addGoal(g);
        }
    }
    /**
	 * @return the gameDescription
	 */
	public String getGameDescription() {
		return gameDescription;
	}
	/**
	 * @param gameDescription the gameDescription to set
	 */
	public void setGameDescription(String gameDescription) {
		this.gameDescription = gameDescription;
	}
	
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	/**
     * Ensure that the game parameters are all set
     * @return true if valid, false otherwise
     */
    public boolean validate() {
        // TODO: This method is way too simple. A more thorough validation must be done!
        return (gameName != null && gameDescription != null);
    }
}