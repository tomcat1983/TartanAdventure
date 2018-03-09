package edu.cmu.tartan.goal;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.item.Item;

import java.util.Vector;

public class GameCollectGoal implements GameGoal {
    private Player player = null;
    private Vector<String> itemsList = null;

    public GameCollectGoal(Vector<String> items, Player p) {
        player = p;
        itemsList = items;
    }

    @Override
    public Boolean isAchieved() {
        int count=0;
        for (String name : itemsList) {
            for (Item collected : player.getCollectedItems()) {
                if (name.equals(collected.description())) {
                    count++;
                }
            }
        }
        return count == itemsList.size();
    }
}
