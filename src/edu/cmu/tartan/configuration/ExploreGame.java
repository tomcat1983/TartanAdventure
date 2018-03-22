package edu.cmu.tartan.configuration;

import edu.cmu.tartan.Game;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.goal.GameExploreGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemCoffee;
import edu.cmu.tartan.room.Room;

import java.util.Vector;

public class ExploreGame extends GameConfiguration {

    public ExploreGame() {
        super.name = "Explorer";
    }

    @Override
    public void configure(Game game) throws InvalidGameException {

        Room room1 = new Room("You are in the first room. There seems to be a room to the North.", "Room1");
        Room room2 = new Room("You are in the second room. You can go South to return to the beginning and you can go East to get to Room 3.", "Room2");
        Room room3 = new Room("You are in the third room. You can go West to return to the Room 2.", "Room3");

        // player would type 'go north'
        room1.setAdjacentRoom(Action.ActionGoNorth, room2);
        room2.setAdjacentRoom(Action.ActionGoEast, room3);

        // player would type 'drink coffee'
        ItemCoffee coffee = (ItemCoffee) Item.getInstance("coffee");
        room2.putItem(coffee);

        Vector<String> goalItems = new Vector<>();
        goalItems.add("room1");
        goalItems.add("room2");
        goalItems.add("room3");

        Player player = new Player(room1);
        game.setPlayer(player);
        game.addGoal(new GameExploreGoal(goalItems,  game.getPlayer()));

        game.setDescription("Explore different connected rooms.");

        if (game.validate() == false) throw new InvalidGameException("Game improperly configured");

        return;
    }
}

