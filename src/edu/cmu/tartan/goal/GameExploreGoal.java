package edu.cmu.tartan.goal;

import edu.cmu.tartan.Game;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.room.Room;

import java.util.Vector;

public class GameExploreGoal implements GameGoal {
    private Vector<String> itinerary = new Vector<>();
    private Player player;

    public GameExploreGoal(Vector<String> places, Player p) {
        itinerary = places;
        player = p;
    }

    @Override
    public Boolean isAchieved() {
        int count=0;
        for (String place : itinerary) {
            for (Room room : player.getRoomsVisited()) {
                if (place.equals(room.shortDescription())) {
                    count++;
                }
            }
        }
        return count == itinerary.size();
    }
}
