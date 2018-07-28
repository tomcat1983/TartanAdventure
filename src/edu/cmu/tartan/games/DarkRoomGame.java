package edu.cmu.tartan.games;

import edu.cmu.tartan.GameConfiguration;
import edu.cmu.tartan.GameContext;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.goal.GameExploreGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.room.Room;
import edu.cmu.tartan.room.RoomDark;

import java.util.ArrayList;

/**
 * A dark room game shows how to traverse a dark room
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class DarkRoomGame extends GameConfiguration {

    /**
     * Create a new dark room game.
     */
    public DarkRoomGame() {
        super("Darkess");
    }

    /**
     * Confugure the game
     * @param game the Game object that will manage execution
     * @throws InvalidGameException
     */
    @Override
    public boolean configure(GameContext context) throws InvalidGameException{

        Room room1 = new Room("You are in the first room. There seems to be a room to the North.", "Room1");
        // player would type 'go north'

        ArrayList<Item> items = new ArrayList<>();
        String classroomDescription = "You are in a classroom.";
        String classroomShortDescription = "Classroom";
        String classroomDarkDescription = "It is dark. Perhaps you can find a way to see...";
        String classroomDarkShortDescription = "Darkness";

        // A flashlight (or any luminous object) is needed or the player will lose!
        items.add(Item.getInstance("flashlight", context.getUserId()));

        RoomDark classroom =
                new RoomDark(classroomDescription,
                        classroomShortDescription,
                        classroomDarkDescription,
                        classroomDarkShortDescription, true);
        room1.putItems(items);
        classroom.setDeathMessage("As walk in the dark room, you trip on a mysterious object. You fall toward the floor, and hit your head against a large rock.");

        room1.setAdjacentRoom(Action.ACTION_GO_NORTH, classroom);

        Player player = new Player(room1, Player.DEFAULT_USER_NAME);
        ArrayList<String> goalItems = new ArrayList<>();
        goalItems.add("room1");
        goalItems.add("Classroom");

        context.setPlayer(player);
        context.addGoal(new GameExploreGoal(goalItems,player));

        context.setGameDescription("Explore a dark room");

        if (!context.validate()) throw new InvalidGameException("Game improperly configured");
        
        return true;
    }
}
