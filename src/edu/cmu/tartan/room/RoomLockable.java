package edu.cmu.tartan.room;

import edu.cmu.tartan.item.Item;

/**
 * The class for a locked room. A key is required to open this type of room
 * from this class.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class RoomLockable extends Room {
	private static final long serialVersionUID = 1L;
	private boolean locked;
    private Item key;
    private String unlockMessage;

    /**
     * Create a new locked room
     * @param description description
     * @param shortDescription short description
     * @param locked indicates whether the room is locked
     * @param key the specific key needed to unlock this toon
     */
	public RoomLockable(String description, String shortDescription, boolean locked, Item key) {
		super(description, shortDescription);

		this.locked = locked;
		this.key = key;
		this.unlockMessage = "Room unlocked.";
	}

	//make room first, set key later
	public RoomLockable(String description, String shortDescription, boolean locked) {
		this(description, shortDescription, locked, null);
	}

	/**
	 * @param key
	 */
	public void setKey(Item key) {
		this.key = key;
	}

    /**
     * Indicate whether room is locked
     * @return
     */
	public boolean isLocked() {
		return this.locked;
	}

    /**
     * Set unlock message
     * @param s the unlock message
     */
	public void setUnlockMessage(String s){
		this.unlockMessage = s;
	}

	/**
	 * @return
	 */
	public String getUnlockMessage(){
		return this.unlockMessage;
	}

    /**
     * Unlock the room
     * @param key the key to use to unlock
     * @return true if it is the right key; false otherwise
     */
	public boolean unlock(Item key) {
		if(this.key.compareTo(key) == 0) {
			this.locked = false;
			return true;
		}
		else {
			return false;
		}
	}
}
