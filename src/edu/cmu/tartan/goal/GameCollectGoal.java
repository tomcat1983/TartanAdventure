package edu.cmu.tartan.goal;

import java.util.ArrayList;

import edu.cmu.tartan.Player;
import edu.cmu.tartan.item.Item;

/**
 * A game goal based on collecting items. If a player collects a designated set of items he/she will achieve this goal.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class GameCollectGoal implements GameGoal {
	private static final long serialVersionUID = 1L;
	private Player player = null;
    private ArrayList<String> itemsList = null;
    private int count=0;

    /**
     * Create a new goal
     * @param items the required items
     * @param p the player
     */
    public GameCollectGoal(ArrayList<String> items, Player p) {
        player = p;
        itemsList = items;
    }

    /**
     * @param items
     */
    public GameCollectGoal(ArrayList<String> items) {
        this(items, null);
    }

    /**
     * @return
     */
    public ArrayList<String> getItemList() {
    	return itemsList;
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

    @Override
	public String getStatus() {
        return "You collected " + count + " out of " + itemsList.size() + " items.";
    }

    @Override
    public String describe() {
        StringBuilder sb = new StringBuilder();
        sb.append("Game Collect Goal: You must collect the following items\n");
        for (String i : itemsList) {
            sb.append(" * " + i + "\n");
        }
        return sb.toString();
    }

    @Override
	public void setPlayer(Player player) {
		this.player = player;
	}
}
