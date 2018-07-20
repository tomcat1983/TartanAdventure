package edu.cmu.tartan.games;

import edu.cmu.tartan.Game;
import edu.cmu.tartan.GameConfiguration;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.goal.GameCollectGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomLockable;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A collection game is one where a player must collect certain items to win.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class CollectGame extends GameConfiguration {

    /**
     * Create a new collect game
     */
    public CollectGame() {
        super("Collector");
    }

    /**
     * Configure the new game
     * @param game the Game object that will manage execution
     * @throws InvalidGameException
     */
    @Override
    public void configure(Game game) throws InvalidGameException {

        Room mid1 = new Room("There is a fork", "Fork");
        Room mid2 = new Room("Ferocious bear", "bear");
        Room end = new RoomLockable("You are inside of a building", "Building interior", true, Item.getInstance("key"));

        end.setAdjacentRoom(Action.ACTION_GO_NORTHEAST, mid1);

        List<Item> startItems = new LinkedList<>();
        startItems.add(Item.getInstance("brick"));
        startItems.add(Item.getInstance("key"));
        startItems.add(Item.getInstance("lock"));
        startItems.add(Item.getInstance("gold"));
        // destroy item
        startItems.add(Item.getInstance("pot"));
        startItems.add(Item.getInstance("microwave"));

        Room start = new Room("There is a tree, with a building to the West. There is a lock on the door.", "Tree" );
        start.setAdjacentRoom(Action.ACTION_GO_NORTH, mid1);
        start.setAdjacentRoom(Action.ACTION_GO_EAST, mid2);
        start.setAdjacentRoom(Action.ACTION_GO_WEST, end);
        start.putItems(startItems);

        // Now we configure the goal based on picking up items
        List<String> goalItems = new ArrayList<>();
        goalItems.add("brick");
        goalItems.add("key");
        goalItems.add("gold");

        // Create the game's player and install the goals
        Player player = new Player(start);
        game.setPlayer(player);
        game.addGoal(new GameCollectGoal(goalItems, player));

        StringBuilder sb = new StringBuilder("Collect the following items:\n");
        sb.append(" * a brick\n");
        sb.append(" * a key\n");
        sb.append(" * a peice of gold\n");

        game.setDescription(sb.toString());

        if (!game.validate()) throw new InvalidGameException("Game improperly configured");
    }
}
