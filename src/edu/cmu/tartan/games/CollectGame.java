package edu.cmu.tartan.games;

import edu.cmu.tartan.GameConfiguration;
import edu.cmu.tartan.GameContext;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.goal.GameCollectGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemFood;
import edu.cmu.tartan.item.StringForItems;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomLockable;

import java.util.ArrayList;

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
    public boolean configure(GameContext context) throws InvalidGameException {

        Room mid1 = new Room("There is a fork", "Fork");
        Room mid2 = new Room("Ferocious bear", "bear");
        Room end = new RoomLockable("You are inside of a building", "Building interior", true, Item.getInstance("key", context.getUserId()));

        end.setAdjacentRoom(Action.ACTION_GO_NORTHEAST, mid1);

        ArrayList<Item> startItems = new ArrayList<>();
        startItems.add(Item.getInstance("brick", context.getUserId()));
        startItems.add(Item.getInstance("key", context.getUserId()));
        startItems.add(Item.getInstance("lock", context.getUserId()));
        startItems.add(Item.getInstance("gold", context.getUserId()));
        // destroy item
        startItems.add(Item.getInstance("pot", context.getUserId()));
        startItems.add(Item.getInstance("food", context.getUserId()));
        startItems.add(Item.getInstance("microwave", context.getUserId()));
        startItems.add(Item.getInstance(StringForItems.CPU, context.getUserId()));
        startItems.add(Item.getInstance(StringForItems.COMPUTER, context.getUserId()));
        Room start = new Room("There is a tree, with a building to the West. There is a lock on the door.", "Tree" );
        start.setAdjacentRoom(Action.ACTION_GO_NORTH, mid1);
        start.setAdjacentRoom(Action.ACTION_GO_EAST, mid2);
        start.setAdjacentRoom(Action.ACTION_GO_WEST, end);
        start.putItems(startItems);

        
        ItemFood food = (ItemFood)Item.getInstance("food", context.getUserId());
        food.setMeltItem(Item.getInstance("diamond", context.getUserId()));
        // Now we configure the goal based on picking up items
        ArrayList<String> goalItems = new ArrayList<>();
        goalItems.add("brick");
        goalItems.add("key");
        goalItems.add("gold");

        // Create the game's player and install the goals
        Player player = new Player(start, Player.DEFAULT_USER_NAME);
        context.setPlayer(player);
        context.addGoal(new GameCollectGoal(goalItems, player));
        
        StringBuilder sb = new StringBuilder("Collect the following items:\n");
        sb.append(" * a brick\n");
        sb.append(" * a key\n");
        sb.append(" * a peice of gold\n");
        context.setGameDescription(sb.toString());

        if (!context.validate()) throw new InvalidGameException("Game improperly configured");
        
        return true;
    }
}
