package edu.cmu.tartan.room;

import java.util.LinkedList;

import edu.cmu.tartan.GameInterface.MessageType;
import edu.cmu.tartan.Player;
import edu.cmu.tartan.TerminateGameException;
import edu.cmu.tartan.action.Action;
import edu.cmu.tartan.item.Item;

/**
 * The class for a room that requires a certain item to enter.
 * from this class.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class RoomRequiredItem extends Room {
	private static final long serialVersionUID = 1L;
	private Item requiredItem;
    private boolean diesOnItemDiscard;
    private boolean diesOnEntry;
    private String deathMessage;
    private LinkedList<Action> safeDirections;
	String warningDescription;
	String warningShortDescription;

    /**
     * Create a new room the requires an item
     * @param d
     * @param dd
     * @param w
     * @param ws
     * @param requiredItem
     */
	public RoomRequiredItem(String d, String dd, String w, String ws, Item requiredItem) {
		super(d, dd);

		this.warningDescription = w;
		this.warningShortDescription = ws;
		this.requiredItem = requiredItem;
		this.safeDirections = new LinkedList<>();
		this.diesOnItemDiscard = false;
		this.diesOnEntry = false;
		this.deathMessage = null;
	}

	//maek room first, set requiredItem later
	public RoomRequiredItem(String d, String dd, String w, String ws) {
		this(d, dd, w, ws, null);
	}

	/**
	 * @param w
	 */
	public void setWarningDescription(String w) {
		this.warningDescription = w;
	}

	/**
	 * @param ws
	 */
	public void setShortWarningDescription(String ws) {
		this.warningShortDescription = ws;
	}

	/**
	 * @param requiredItem
	 */
	public void setRequiredItem(Item requiredItem) {
		this.requiredItem = requiredItem;
	}


	/**
	 * @throws TerminateGameException
	 */
	public void playerDidDropRequiredItem() throws TerminateGameException {
		if(this.diesOnItemDiscard) {
			gameInterface.println(getPlayer().getUserName(), MessageType.PRIVATE, this.deathMessage);
			this.getPlayer().terminate();
		}
		else {
			this.getPlayer().lookAround();
		}
	}
	/**
	 * @param b
	 */
	public void setPlayerDiesOnItemDiscard(boolean b) {
		this.diesOnItemDiscard = b;
	}

	/**
	 * @param b
	 */
	public void setPlayerDiesOnEntry(boolean b) {
		this.diesOnEntry = b;
	}

	/**
	 * @return
	 */
	public boolean diesOnEntry() {
		return this.diesOnEntry;
	}

	/**
	 * @param a
	 * @param player
	 * @return
	 */
	public boolean shouldLoseForAction(Action a, Player player) {
		return !this.safeDirections.contains(a) && !player.hasItem(this.requiredItem);
	}

	/**
	 * @param s
	 */
	public void setLoseMessage(String s) {
		this.deathMessage = s;
	}

	/**
	 * @return
	 */
	public String loseMessage() {
		return this.deathMessage;
	}

	/**
	 * @return
	 */
	public Item requiredItem() {
		return this.requiredItem;
	}

	/**
	 * @param direction
	 * @return
	 */
	public boolean setSafeDirection(Action direction) {
		return safeDirections.add(direction);
	}

	@Override
	public String toString() {

		if(this.getPlayer().hasItem(this.requiredItem)) {
			return super.toString();
		}
		else {
			return this.warningDescription;
		}
	}

	@Override
	public String description() {
		if(this.getPlayer().hasItem(this.requiredItem)) {
			String s = this.roomWasVisited ? this.shortDescription : this.description + visibleItems();
			this.roomWasVisited = true;
			return s;
		}
		return "You cannot visit this room";
	}
}
