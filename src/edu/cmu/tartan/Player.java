package edu.cmu.tartan;

import edu.cmu.tartan.GameInterface.MessageType;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.goal.GameGoal;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.item.ItemMagicBox;
import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.properties.Luminous;
import edu.cmu.tartan.properties.Valuable;
import edu.cmu.tartan.room.*;

import java.util.Map;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The player for a game.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class Player implements Comparable, Serializable {
	public static final String DEFAULT_USER_NAME = "Tony";
	/**
	 * Version for serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Game interface for game message and log
	 */
	private static final transient GameInterface gameInterface = GameInterface.getInterface();

	/**
     * The player's score.
     */
    private int score=0;

    /**
     * The player's name
     */
    private String userName;

	/**
     * The list of rooms that this player has visited.
     */
    private ArrayList<Room> roomsVisited = new ArrayList<>();

    /**
     * The points that this player can possibly score.
     */
    private int possiblePoints=0;

    /**
     * The inventory of items this player has.
     */
    private ArrayList<Item> items = new ArrayList<>();

    /**
     * This player's goals
     */
    private ArrayList<GameGoal> goals = new ArrayList<>();

    /**
     * The current room this player is in.
     */
    private Room currentRoom = null;

    /**
     * Player constructor
     *
     * @param currentRoom the current room
     */
    public Player(Room currentRoom, String userName) {
        this(currentRoom, new ArrayList<>(), userName);
    }

    /**
     * Player constructor for player with items
     * @param currentRoom the current room
     * @param items the player's items
     */
    public Player(Room currentRoom, ArrayList<Item>items, String userName) {
        this.items = items;
        this.score = 0;
        this.currentRoom = currentRoom;
        this.currentRoom.setPlayer(this);
        this.userName = userName;
    }

    /**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

    /**
     * Drop an item
     * @param item the item to drop.
     * @return The dropped item or null if the item cannot be found.
     */
    public Item drop(Item item) {
        if(this.items.remove(item)) {
        	int s = item.value();
            this.score -= s;
            gameInterface.println(userName, MessageType.PRIVATE, "You lost " + s + GamePlayMessage.POINTS);
        	gameInterface.println(userName, MessageType.OTHER, userName + " lost " + s + GamePlayMessage.POINTS);
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
        	gameInterface.println(userName, MessageType.PRIVATE, "You don't have this item to drop");
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
    public ArrayList<Item> getCollectedItems() {
        return this.items;
    }
    
    /**
     * Put the direct item in the indirect item.
     * @return the install() result.
     */
    public boolean putItemInItem(Item direct, Item indirect) {
        boolean ret = ((Hostable)indirect).install(direct);
        if(ret && indirect instanceof ItemMagicBox && direct instanceof Valuable) {
        	score((Valuable)direct);
        }
        return ret;
    }

    private void requestDelay(String message, int delay) {
    	if(message != null) {
            if(delay != 0) {
                for(int i=0; i < 3; i++) {
                    gameInterface.println(userName, MessageType.PRIVATE, "...");
                    try{
                        Thread.sleep(delay);
                    }
                    catch(Exception e1) {
                        // pass
                    }
                }
            }
            gameInterface.println(userName, MessageType.PRIVATE, message);
        }
    }
    /**
     * Move the player to a new room.
     * @param nextRoom the new room.
     * @throws TerminateGameException 
     */
    public void move(Room nextRoom) throws TerminateGameException {

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
                gameInterface.println(userName, MessageType.PRIVATE, r.loseMessage());
                terminate();
            }
        }

        this.currentRoom = nextRoom;
        saveRoom(currentRoom);
        gameInterface.println(userName, MessageType.OTHER, userName + "is in " + currentRoom.shortDescription());
        gameInterface.println(userName, MessageType.PRIVATE, this.currentRoom.description());
    }

    /**
     * Save the newly visited room to the list of rooms visited.
     * @param room The room to save.
     */
    protected void saveRoom(Room room) {

        roomsVisited.add(room);
    }

    /**
     * Get the list of rooms visited.
     * @return The list of visited rooms.
     */
    public ArrayList<Room> getRoomsVisited() {
        return roomsVisited;
    }

    private void roomRequiredItemCheck(RoomRequiredItem room, Action action) throws TerminateGameException  {
    	if(room.shouldLoseForAction(action)) {
            gameInterface.println(userName, MessageType.PRIVATE, room.loseMessage());
            terminate();
        }
    }
    
    private void roomRequiredLuminousItem(RoomDark room) throws TerminateGameException {
    	if(room.isDark() && !hasLuminousItem()) {
            gameInterface.println(userName, MessageType.PRIVATE, room.deathMessage());
            terminate();
        }
    }
    
    private boolean isNextRoomRequiredCheck(Room nextRoom) throws TerminateGameException {
    	if(nextRoom instanceof RoomLockable) {
            RoomLockable lockedRoom = (RoomLockable)nextRoom;
            if(lockedRoom.isLocked()) {
                gameInterface.println(userName, MessageType.PRIVATE, "This door is locked.");
                return true;
            }
        }
        else if(nextRoom instanceof RoomObscured) {
            RoomObscured obscuredRoom = (RoomObscured)nextRoom;
            if(obscuredRoom.isObscured()) {
                gameInterface.println(userName, MessageType.PRIVATE, "You can't move that way.");
                return true;
            }
        }
    	return false;
    }
    
    /**
     * Move version two based on an action
     * @param action the action associated with the move.
     * @throws TerminateGameException 
     */
    public boolean move(Action action) throws TerminateGameException {

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
            gameInterface.println(userName, MessageType.PRIVATE, "You can't move that way.");
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
    public boolean lookAround() {
        gameInterface.println(userName, MessageType.PRIVATE, currentRoom.toString());
        return true;
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
    	gameInterface.println(userName, MessageType.PRIVATE, "You scored " + s + GamePlayMessage.POINTS);
    	gameInterface.println(userName, MessageType.OTHER, userName + " scored " + s + GamePlayMessage.POINTS);
    	score += s;
    }

    /**
     * Terminate this player.
     */
    public void terminate() throws TerminateGameException {
    	gameInterface.println(userName, MessageType.OTHER, userName +" have scored " + this.score + " out of  " + possiblePoints + " possible points.");
        gameInterface.println(userName, MessageType.PRIVATE, "You have scored " + this.score + " out of  " + possiblePoints + " possible points.");
        throw new TerminateGameException("Terminate Game");
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
    public ArrayList<GameGoal> getGoals() {
        return goals;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
    public int compareTo(Object other) {
        if (userName.compareTo(((Player) other).userName) == 0) {
            return 0;
        }
        return -1;
    }

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Player) {
			Player player = (Player)obj;
    		if(player.userName.equals(userName)) {
    			return true;
    		}
    	}
		return false;
	}
}