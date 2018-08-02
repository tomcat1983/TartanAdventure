package edu.cmu.tartan.goal;

import java.util.List;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.room.Room;

/**
 * A game goal based on exploration of different areas. Traveling to each place is necessary to achieve this goal.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class GameExploreGoal implements GameGoal {
    private static final long serialVersionUID = 1L;
	// the list of places required to visit
    private List<String> itinerary;
    // progress towards the goal
    private int count = 0;

    private Player player;

    /**
     * Create a new exploration goal.
     * @param places the list of places to explore
     * @param p the player
     */
    public GameExploreGoal(List<String> places, Player p) {
        itinerary = places;
        player = p;
    }

    /**
     * @param places
     */
    public GameExploreGoal(List<String> places) {
        this(places, null);
    }


    /**
     * @return
     */
    public List<String> getItinerary() {
    	return itinerary;
    }

    /**
     * Describe the goal.
     * @return a description of the goal.
     */
    @Override
    public String describe() {
        StringBuilder sb = new StringBuilder();
        sb.append("Game Explore Goal: You must visit the following rooms\n");
        for (String i : itinerary) {
            sb.append(" * " + i + "\n");
        }
        return sb.toString();
    }

    /**
     * Fetch status of this goal
     * @return a displayable string for progress towards this goal
     */
    @Override
	public String getStatus() {
        return "You have explored " + count + " out of " + itinerary.size() + " rooms.";
    }

    /**
     * Evaluate whether this goal is achieved.
     *
     * @return true if the goal is achieved; false otherwise
     */
    @Override
    public Boolean isAchieved() {
        int newCount = 0;
        for (String place : itinerary) {
            for (Room room : player.getRoomsVisited()) {
                if (place.equalsIgnoreCase(room.shortDescription())) {
                    newCount++;
                }
            }
        }
        count = newCount;
        return count == itinerary.size();
    }

	@Override
	public void setPlayer(Player player) {
		this.player = player;
	}
}