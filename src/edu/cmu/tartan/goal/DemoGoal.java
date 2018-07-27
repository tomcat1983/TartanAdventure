package edu.cmu.tartan.goal;

import edu.cmu.tartan.Player;

public class DemoGoal implements GameGoal {
    // the list of places required to visit
    private Player player = null;

    @Override
    public String describe() {
        return "Demo goal";
    }

    /**
     * Fetch status of this goal
     * @return a displayable string for progress towards this goal
     */
    public String getStatus() {
        return "Demo status";
    }

    /**
     * Evaluate whether this goal is achieved.
     *
     * @return true if the goal is achieved; false otherwise
     */
    @Override
    public Boolean isAchieved() {
        return false;
    }
    
    @Override
	public void setPlayer(Player player) {
		this.player = player;
	}
}