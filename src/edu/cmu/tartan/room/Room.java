package edu.cmu.tartan.room;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import edu.cmu.tartan.GameInterface;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.item.Item;
import edu.cmu.tartan.properties.Valuable;
import edu.cmu.tartan.properties.Visible;

/**
 * The main class for a room. All room types must extend this class.
 * from this class.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class Room implements Comparable, Serializable {
	/**
	 * Version for serialization
	 */
	private static final long serialVersionUID = 1L;

	public static final String DEFAULT_DESC = "You are in a room";
	public static final String DEFAULT_SHORT_DESC = "Room";

	/**
	 * Game logger for game log
	 */
	protected static final transient Logger gameLogger = Logger.getGlobal();

	/**
	 * Game interface for game message
	 */
	protected static final transient GameInterface gameInterface = GameInterface.getInterface();

    // Room description
    protected String description;
    protected String shortDescription;

    // adjacent rooms
    protected EnumMap<Action, Room> adjacentRooms;

    // transition information for what happens when a room is left/entered
    protected EnumMap<Action, String> transitionMessages;
    protected int transitionDelay;

    // indicates that a room was visited
    protected boolean roomWasVisited;

    // items in the room
    private ArrayList<Item> items;

	// the player within the room
    private Player player;

    /**
     * Create a new room
     */
    public Room() {
        this(DEFAULT_DESC, DEFAULT_SHORT_DESC);
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Create a room with default descriptions
     * @param description the room description
     * @param shortDescription the short room description
     */
    public Room(@NonNull String description,@NonNull String shortDescription) {
        this.roomWasVisited = false;
        this.description = description;
        this.shortDescription = shortDescription;
        this.items = new ArrayList<>();
        this.adjacentRooms = new EnumMap<>(Action.class);
        this.transitionMessages = new EnumMap<>(Action.class);
        this.transitionDelay = 0;
    }

    /**
     * Set the adjacent room
     * @param a action required to get to this room
     * @param r the adjacent room
     */
    public boolean setAdjacentRoom(@NonNull Action a,@NonNull Room r) {
    	if(isAdjacentToRoom(r)) {
    		return false;
    	}
        Room ret = setOneWayAdjacentRoom(a, r);
        if(ret!=r) {
        	ret = r.setOneWayAdjacentRoom(a.getOppositeDirection(), this);
        	if(ret!=r) {
        		return true;
        	}
        }
        return false;
    }

    /**
     * Set an adjacent room that a player cannot return from
     * @param a action required to get to this room
     * @param r the adjacent room
     */
    public Room setOneWayAdjacentRoom(@NonNull Action a, @NonNull Room r) {
        return adjacentRooms.put(a, r);
    }

    /**
     * Fetch the room for a given direction (action)
     * @param a action required to get to this room
     */
    public Room getRoomForDirection(@NonNull Action a) {
        if (canMoveToRoomInDirection(a)) {
            return adjacentRooms.get(a);
        }
        return null;
    }

    /**
     * Fetch the direction (as an action) for a given room
     * @param room room for the specified direction to get to this room
     * @return the action
     */
    public Action getDirectionForRoom(@NonNull Room room) {
        for (Entry<Action, Room> entry: adjacentRooms.entrySet()) {
        	Action action = entry.getKey();
            if (adjacentRooms.get(action).compareTo(room) == 0) {
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
    public boolean canMoveToRoomInDirection(@NonNull Action a) {
        return adjacentRooms.containsKey(a);
    }

    /**
     * @param message
     * @param direction
     */
    public void setAdjacentRoomTransitionMessage(@NonNull String message, @NonNull Action direction) {
        transitionMessages.put(direction, message);
    }

    /**
     * @param message
     * @param direction
     * @param delay
     */
    public void setAdjacentRoomTransitionMessageWithDelay(@NonNull String message, @NonNull Action direction, int delay) {
        setAdjacentRoomTransitionMessage(message, direction);
        transitionDelay = delay;
    }

    /**
     * Test if two rooms are connected
     * @param other the other room
     * @return true if the rooms are connected; false otherwise
     */
    public boolean isAdjacentToRoom(@Nullable Room other) {
    	if(other==null) {
    		return false;
    	}

        for (Room room : adjacentRooms.values()) {
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
    public Map<Action, String> transitionMessages() {
        return transitionMessages;
    }

    /**
     *  Get transition delay
     * @return the delay
     */
    public int transitionDelay() {
        return transitionDelay;
    }

    /**
     * Place an item in the room
     * @param item the item to add
     * @return true successful, false failure
     */
    public boolean putItem(@NonNull Item item) {
        return items.add(item);
    }

    /**
     * Put a list of items in a room
     * @param items the items
     * @return true successful, false failure
     */
    public boolean putItems(@NonNull ArrayList<Item> items) {
    	Iterator<Item> iter = items.iterator();
    	int insertSize = 0;
    	while (iter.hasNext()) {
    		Item item = iter.next();
    	    this.items.add(item);
    	    insertSize++;
    	}

    	return (items.size()==insertSize);
    }

    /**
     * Remove an item from the room
     * @param item the item to remove
     * @return the removed item
     */
    public Item remove(@NonNull Item item) {
        if (items.contains(item) && item instanceof Valuable) {
        	items.remove(item);
            return item;
        }
        return null;
    }

    /**
     * Check if a room contains an item (assuming it is not invisible
     * @param item the item to check for
     * @return true if the room contains the item; false otherwise
     */
    public boolean hasItem(@NonNull Item item) {
    	// if the item is invisible, then fool the player
    	if (!item.isVisible()) {
        	return false;
        } else {
        	return items.contains(item);
        }
    }

    /**
     * @return
     */
    public ArrayList<Item> getItems() {
		return items;
	}

	/**
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}

    /**
     * @param player
     */
    public void setPlayer(@NonNull Player player) {
        this.player = player;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
	public String toString() {
        return description + visibleItems();
    }

    /**
     * Fetch a list of visible items for a room
     * @return the list of items
     */
    public String visibleItems() {
    	StringBuilder  s = new StringBuilder("");
        for (Item item : items) {
            if (item instanceof Visible && item.isVisible()) {
                s.append("\nThere is a '").append(item.detailDescription()).append("' (i.e. " + item.description() + " ) here.");
            }
        }
        return s.toString();
    }

    /**
     * @return
     */
    public String description() {
        String d = roomWasVisited ? shortDescription : description + visibleItems();
        roomWasVisited = true;
        return d;
    }

    /**
     * @return
     */
    public String shortDescription() {
        return shortDescription;
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
