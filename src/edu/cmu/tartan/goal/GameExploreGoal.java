package edu.cmu.tartan.goal;

import edu.cmu.tartan.Game;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.room.Room;

import java.util.Vector;

public class GameExploreGoal implements GameGoal {
    private Vector<String> itinerary = new Vector<>();
    private int count = 0;
    private Player player;

    public GameExploreGoal(Vector<String> places, Player p) {
        itinerary = places;
        player = p;
    }

    @Override
    public String describe() {
        StringBuilder sb = new StringBuilder();
        sb.append("The objective of this type of game is to visit the following rooms:");
        for (String i : itinerary) {
            sb.append(" * " + i + "\n");
        }
        return sb.toString();
    }

    public String getStatus() {
        return "You have explored " + count + " out of " + itinerary.size() + " rooms.";
    }

    @Override
    public Boolean isAchieved() {

        for (String place : itinerary) {
            for (Room room : player.getRoomsVisited()) {
                if (place.equalsIgnoreCase(room.shortDescription())) {
                    this.count++;
                }
            }
        }
        return count == itinerary.size();
    }
}
