package edu.cmu.tartan.goal;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.item.Item;

import java.util.Vector;

public class GameCollectGoal implements GameGoal {
    private Player player = null;
    private Vector<String> itemsList = null;
    private int count=0;

    public GameCollectGoal(Vector<String> items, Player p) {
        player = p;
        itemsList = items;
    }

    @Override
    public Boolean isAchieved() {
        int newCount=0;
        for (String name : itemsList) {
            for (Item collected : player.getCollectedItems()) {
                if (name.equals(collected.description())) {
                    newCount++;
                }
            }
        }
        count = newCount;
        return count == itemsList.size();
    }

    public String getStatus() {
        return "You collected " + count + " out of " + player.getCollectedItems().size() + " items.";
    }

    @Override
    public String describe() {
        StringBuilder sb = new StringBuilder();
        sb.append("The objective of this type of game is to collect the following items:");
        for (String i : itemsList) {
            sb.append(" * " + i + "\n");
        }
        return sb.toString();
    }
}
