package edu.cmu.tartan.room;

import edu.cmu.tartan.GameInterface;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.properties.Valuable;
import edu.cmu.tartan.properties.Visible;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.LinkedList;

/**
 * The main class for a room. All room types must extend this class.
 * from this class.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class Room implements Comparable {
	/**
	 * Game interface for game message and log
	 */
	protected GameInterface gameInterface = GameInterface.getInterface();

    // Room description
    protected String description;
    protected String shortDescription;

    // adjacent rooms
    protected HashMap<Action, Room> adjacentRooms;

    // transition information for what happens when a room is left/entered
    protected HashMap<Action, String> transitionMessages;
    protected int transitionDelay;

    // indicates that a room was visited
    protected boolean roomWasVisited;

    // items in the room
    private List<Item> items;
	
	// the player within the room
    private Player player;

    /**
     * Create a new room
     */
    public Room() {
        this("You are in a room", "Room");
    }

    /**
     * Create a room with default descriptions
     * @param description the room description
     * @param shortDescription the short room description
     */
    public Room(String description, String shortDescription) {

        this.roomWasVisited = false;
        this.description = description;
        this.shortDescription = shortDescription;
        this.items = new LinkedList<>();
        this.adjacentRooms = new HashMap<>();
        this.transitionMessages = new HashMap<>();
        this.transitionDelay = 0;
    }

    /**
     * Set the adjacent room
     * @param a action required to get to this room
     * @param r the adjacent room
     */
    public void setAdjacentRoom(Action a, Room r) {
        setOneWayAdjacentRoom(a, r);
        r.setOneWayAdjacentRoom(a.getOppositeDirection(), this);
    }

    /**
     * Set an adjacent room that a player cannot return from
     * @param a action required to get to this room
     * @param r the adjacent room
     */
    public void setOneWayAdjacentRoom(Action a, Room r) {
        this.adjacentRooms.put(a, r);
    }

    /**
     * Fetch the room for a given direction (action)
     * @param a action required to get to this room
     */
    public Room getRoomForDirection(Action a) {
        if (canMoveToRoomInDirection(a)) {
            return this.adjacentRooms.get(a);
        }
        return null;
    }

    /**
     * Fetch the direction (as an action) for a given room
     * @param room room for the specified direction to get to this room
     * @return the action
     */
    public Action getDirectionForRoom(Room room) {
        for (Entry<Action, Room> entry: adjacentRooms.entrySet()) {
        	Action action = entry.getKey();
            if (this.adjacentRooms.get(action).compareTo(room) == 0) {
                return action;
            }
        }
        return Action.ACTION_UNKNOWN;
    }

    /**
     * Test is the player can enter a room in a given direction
     * @param a the direction
     * @return true if the room is accessible; false otherwise
     */
    public boolean canMoveToRoomInDirection(Action a) {
        return this.adjacentRooms.containsKey(a);
    }

    public void setAdjacentRoomTransitionMessage(String message, Action direction) {
        this.transitionMessages.put(direction, message);
    }

    public void setAdjacentRoomTransitionMessageWithDelay(String message, Action direction, int delay) {
        this.setAdjacentRoomTransitionMessage(message, direction);
        this.transitionDelay = delay;
    }

    /**
     * Test if two rooms are connected
     * @param other the other room
     * @return true if the rooms are connected; false otherwise
     */
    public boolean isAdjacentToRoom(Room other) {
        for (Room room : this.adjacentRooms.values()) {
            if (other.compareTo(room) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Fetch the appropriate transition message
     * @return the message/action pair
     */
    public HashMap<Action, String> transitionMessages() {
        return this.transitionMessages;
    }

    /**
     *  Get transition delay
     * @return the delay
     */
    public int transitionDelay() {
        return this.transitionDelay;
    }

    /**
     * Place an item in the room
     * @param item the item to add
     */
    public void putItem(Item item) {
        this.items.add(item);
    }

    /**
     * Put a list of items in a room
     * @param items the items
     */
    public void putItems(List<Item> items) {
        for (Item i : items) {
            this.items.add(i);
        }
    }

    public Item takeItem(Item item) {
        if (item == null) {
            return null;
        } else {
            gameInterface.println("I don't see that here.");
        }
        return Item.getInstance("unknown");
    }

    /**
     * Remove an item from the room
     * @param item the item to remove
     * @return the removed item
     */
    public Item remove(Item item) {
        if (this.items.contains(item) && item instanceof Valuable) {
        	this.items.remove(item);
            return item;
        }
        return null;
    }

    /**
     * Check if a room contains an item (assuming it is not invisible
     * @param item the item to check for
     * @return true if the room contains the item; false otherwise
     */
    public boolean hasItem(Item item) {
    	// if the item is invisible, then fool the player
    	if (item == null) {
        	return false;
        } else if (!item.isVisible()) {
        	return false;
        } else {
        	return this.items.contains(item);
        }
    }

    public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
    
	public Player getPlayer() {
		return player;
	}

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String toString() {
        return this.description + visibleItems();
    }

    /**
     * Fetch a list of visible items for a room
     * @return the list of items
     */
    public String visibleItems() {
    	StringBuilder  s = new StringBuilder("");
        for (Item item : this.items) {
            if (item instanceof Visible && item.isVisible()) {
            	
                s.append("\nThere is a '").append(item.detailDescription()).append("' (i.e. " + item.description() + " ) here.");
            }
        }
        return s.toString();
    }

    public String description() {
        String d = this.roomWasVisited ? this.shortDescription : this.description + visibleItems();
        this.roomWasVisited = true;
        return d;
    }

    public String shortDescription() {
        return this.shortDescription;
    }

    @Override
    public int compareTo(Object other) {
        if (shortDescription.compareTo(((Room) other).shortDescription()) == 0) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof Room) {
    		Room room = (Room)obj;
    		if(room.description.equals(description) && room.shortDescription.equals(shortDescription)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    @Override
    public int hashCode() {
        return description.hashCode()+shortDescription.hashCode();
    }
}
