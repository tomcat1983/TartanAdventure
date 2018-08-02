package edu.cmu.tartan;

import java.io.Serializable;
import java.util.ArrayList;

import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.room.Room;

public class GameContext implements Comparable, Serializable {
    /**
	 * serial version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
     * The set of goals for a game
     */
    private ArrayList<GameGoal> goals = new ArrayList<>();
    /**
     * The set of rooms for a game(for validation only)
     */
    private ArrayList<Room> rooms = new ArrayList<>();
	/**
     * The player for the game
     */
    private Player player;
	/**
     * The name and description of the active game
     */
    private String gameName;
    private String gameDescription;
    /**
     * The userId is user name with local game. But if game is network mode, user name set userId.
     */
    private String userId;

    /**
     * @param userId
     */
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
	 * @return
	 */
	public ArrayList<Room> getRooms() {
		return rooms;
	}

	/**
	 * @param rooms
	 */
	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
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

	/**
	 * @return
	 */
	public String getGameName() {
		return gameName;
	}
	/**
	 * @param gameName
	 */
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	/**
	 * Ensure that the game parameters are all set
	 * @return true if valid, false otherwise
	 */
	public boolean validate() {
		if(gameName == null || gameDescription == null)
			return false;

		GameValidate validater = new GameValidate(this);

		return validater.check().isEmpty();
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
    public int compareTo(Object other) {
        if (userId.compareTo(((GameContext) other).userId) == 0) {
            return 0;
        }
        return -1;
    }

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof GameContext) {
			GameContext context = (GameContext)obj;
    		if(context.userId.equals(userId)) {
    			return true;
    		}
    	}
		return false;
	}
}
