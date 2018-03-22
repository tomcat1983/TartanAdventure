package edu.cmu.tartan.configuration;

import edu.cmu.tartan.Game;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.goal.GameCollectGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomLockable;

import java.util.LinkedList;
import java.util.Vector;

public class CollectGame extends GameConfiguration {

    public CollectGame() {
        super.name = "Collector";
    }
    @Override
    public void configure(Game game) throws InvalidGameException {

        Room mid1 = new Room("There is a fork", "Fork");
        Room mid2 = new Room("Ferocious bear", "bear");
        Room end = new RoomLockable("You are inside of a building", "Building interior", true, Item.getInstance("key"));

        end.setAdjacentRoom(Action.ActionGoNortheast, mid1);

        LinkedList<Item> startItems = new LinkedList<Item>();
        startItems.add(Item.getInstance("brick"));
        startItems.add(Item.getInstance("key"));
        startItems.add(Item.getInstance("lock"));
        startItems.add(Item.getInstance("gold"));

        Room start = new Room("There is a tree, with a building to the West. There is a lock on the door.", "Tree" );
        start.setAdjacentRoom(Action.ActionGoNorth, mid1);
        start.setAdjacentRoom(Action.ActionGoEast, mid2);
        start.setAdjacentRoom(Action.ActionGoWest, end);
        start.putItems(startItems);

        // Now we configure the goal based on picking up items
        Vector<String> goalItems = new Vector<>();
        goalItems.add("brick");
        goalItems.add("key");
        goalItems.add("gold");

        Player player = new Player(start);
        game.setPlayer(player);
        game.addGoal(new GameCollectGoal(goalItems, player));

        StringBuilder sb = new StringBuilder("Collect the following items:\n");
        sb.append(" * a brick\n");
        sb.append(" * a key\n");
        sb.append(" * a peice of gold\n");

        game.setDescription(sb.toString());

        if (game.validate() == false) throw new InvalidGameException("Game improperly configured");

        return;
    }
}
