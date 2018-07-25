package edu.cmu.tartan;

import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemMagicBox;
import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.properties.Luminous;
import edu.cmu.tartan.properties.Valuable;
import edu.cmu.tartan.room.*;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * The player for a game.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class Player {

	/**
	 * Game interface for game message and log
	 */
	private GameInterface gameInterface = GameInterface.getInterface();

	/**
     * The player's score.
     */
    private int score=0;

    /**
     * The list of rooms that this player has visited.
     */
    private List<Room> roomsVisited = new ArrayList<>();

    /**
     * The points that this player can possibly score.
     */
    private int possiblePoints=0;

    /**
     * The inventory of items this player has.
     */
    private List<Item> items = new ArrayList<>();

    /**
     * This player's goals
     */
    private List<GameGoal> goals = new ArrayList<>();

    /**
     * The current room this player is in.
     */
    private Room currentRoom = null;

    /**
     * Player constructor
     *
     * @param currentRoom the current room
     */
    public Player(Room currentRoom) {
        this(currentRoom, new ArrayList<>());
    }

    /**
     * Player constructor for player with items
     * @param currentRoom the current room
     * @param items the player's items
     */
    public Player(Room currentRoom, List<Item>items) {
        this.items = items;
        this.score = 0;
        this.currentRoom = currentRoom;
        this.currentRoom.setPlayer(this);
    }

    /**
     * Drop an item
     * @param item the item to drop.
     * @return The dropped item or null if the item cannot be found.
     */
    public Item drop(Item item) {
        if(this.items.remove(item)) {
            this.score -= item.value();
            return item;
        }
        else {
            return null;
        }
    }

    /**
     * Get the player's score.
     * @return the score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Drop an item in the player's possession.
     *
     * @param item the item to drop. The dropped item remains in the room.
     * @return true if the item is dropped, false otherwise.
     */
    public boolean dropItem(Item item) {

        Item dropped = drop(item);
        if (dropped == null) {
        	gameInterface.println("You don't have this item to drop");
            return false;
        }
        this.currentRoom.putItem(dropped);
        return true;
    }

    /**
     * Pickup an item.
     * @param item the item to pickup.
     * @return true
     */
    public boolean pickup(Item item){

        this.grabItem(item);
        return true;
    }

    /**
     * Actually add the item to the player's inventory.
     * @param item
     */
    public void grabItem(Item item) {
        this.items.add(item);
    }

    public boolean hasItem(Item item) {
        if(item == null) return false;
        return this.items.contains(item);
    }

    /**
     * Return true if the player has a luminous object
     * @return true if they have a luminous object, false otherwise.
     */
    public boolean hasLuminousItem() {
        for (Item item : this.items) {
            if (item instanceof Luminous) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the current set of items.
     * @return the items.
     */
    public List<Item> getCollectedItems() {
        return this.items;
    }

    public void putItemInItem(Item direct, Item indirect) {
        ((Hostable)indirect).install(direct);
        if(indirect instanceof ItemMagicBox && direct instanceof Valuable) {
            score((Valuable)direct);
        }
    }

    private void requestDelay(String message, int delay) {
    	if(message != null) {
            if(delay != 0) {
                for(int i=0; i < 3; i++) {
                    gameInterface.println("...");
                    try{
                        Thread.sleep(delay);
                    }
                    catch(Exception e1) {
                        // pass
                    }
                }
            }
            gameInterface.println(message);
        }
    }
    /**
     * Move the player to a new room.
     * @param nextRoom the new room.
     */
    public void move(Room nextRoom) {

        nextRoom.setPlayer(this);
        if(this.currentRoom != null && nextRoom.compareTo(this.currentRoom) != 0) {
            Action directionOfTravel = this.currentRoom.getDirectionForRoom(nextRoom);
            Map<Action, String> messages = this.currentRoom.transitionMessages();
            String message = messages.get(directionOfTravel);
            int delay = this.currentRoom.transitionDelay();
            requestDelay(message, delay);
        }
        if(nextRoom instanceof RoomRequiredItem) {
            RoomRequiredItem r = (RoomRequiredItem)nextRoom;
            if(r.diesOnEntry()) {
                gameInterface.println(r.loseMessage());
                this.terminate();
            }
        }

        this.currentRoom = nextRoom;
        saveRoom(currentRoom);
        gameInterface.println(this.currentRoom.description());
    }

    /**
     * Save the newly visited room to the list of rooms visited.
     * @param room The room to save.
     */
    private void saveRoom(Room room) {

        roomsVisited.add(room);
    }

    /**
     * Get the list of rooms visited.
     * @return The list of visited rooms.
     */
    public List<Room> getRoomsVisited() {
        return roomsVisited;
    }

    private void roomRequiredItemCheck(RoomRequiredItem room, Action action)  {
    	if(room.shouldLoseForAction(action)) {
            gameInterface.println(room.loseMessage());
            terminate();
        }
    }
    
    private void roomRequiredLuminousItem(RoomDark room) {
    	if(room.isDark() && !hasLuminousItem()) {
            gameInterface.println(room.deathMessage());
            terminate();
        }
    }
    
    private boolean isNextRoomRequiredCheck(Room nextRoom) {
    	if(nextRoom instanceof RoomLockable) {
            RoomLockable lockedRoom = (RoomLockable)nextRoom;
            if(lockedRoom.isLocked()) {
                if(lockedRoom.causesDeath()) {
                    gameInterface.println(lockedRoom.deathMessage());
                    this.terminate();
                }
                gameInterface.println("This door is locked.");
                return true;
            }
        }
        else if(nextRoom instanceof RoomObscured) {
            RoomObscured obscuredRoom = (RoomObscured)nextRoom;
            if(obscuredRoom.isObscured()) {
                gameInterface.println("You can't move that way.");
                return true;
            }
        }
    	return false;
    }
    
    /**
     * Move version two based on an action
     * @param action the action associated with the move.
     */
    public boolean move(Action action) {

        if(this.currentRoom instanceof RoomRequiredItem) {
            RoomRequiredItem room = (RoomRequiredItem)this.currentRoom;
            roomRequiredItemCheck(room, action);
        }
        else if(this.currentRoom instanceof RoomDark) {
            RoomDark room = (RoomDark)this.currentRoom;
            roomRequiredLuminousItem(room);
        }

        if(this.currentRoom.canMoveToRoomInDirection(action)) {
            Room nextRoom = this.currentRoom.getRoomForDirection(action);
            if(isNextRoomRequiredCheck(nextRoom)) {
            	return false;
            }
            move(nextRoom);
            return true;
        }
        else {
            gameInterface.println("You can't move that way.");
            return false;
        }
    }

    /**
     * Get the current room.
     * @return the current room.
     */
    public Room currentRoom() {
        return this.currentRoom;
    }

    /**
     * Add a goal for this player
     * @param g the new goal.
     */
    public void addGoal(GameGoal g) {
        goals.add(g);
    }

    /**
     * Print information about the room
     */
    public void lookAround() {
        gameInterface.println(this.currentRoom.toString());
    }

    /**
     * Score by doing something with a Valuable valuableObject.
     * @param valuableObject the valuable valuableObject.
     * @see Valuable
     */
    public void score(Valuable valuableObject) {
        score(valuableObject.value());
    }

    /**
     * Add to the Player's score
     * @param s the newly scored points.
     */
    public void score(int s) {
        gameInterface.println("You scored " + s + " points.");
        score += s;
    }

    /**
     * Terminate this player.
     */
    public void terminate() {
        gameInterface.println("You have scored " + this.score + " out of  " + possiblePoints + " possible points.");
        System.exit(0);
    }

    /**
     * Add points available to this player.
     * @param p The new points available.
     */
    public void addPossiblePoints(int p) {
        possiblePoints += p;
    }

    /**
     * Get the points available to this player.
     * @return the available points.
     */
    public int getPossiblePoints() {
        return possiblePoints;
    }

    /**
     * Fetch the goals for this Player.
     * @return the list of this Player's goals.
     */
    public List<GameGoal> getGoals() {
        return goals;
    }
}
