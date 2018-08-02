package edu.cmu.tartan.room;

/**
 * The class for a dark room. Players must have a Luminous item to enter this room safely
 * from this class.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class RoomDark extends Room {

	private static final long serialVersionUID = 1L;
	private static final String DEATH_MESSAGE = "You are dead.";
	// Descriptions
	private String darkDescription;
	private String darkShortDescription;

	// indicates whether the room is dark
	private boolean isDark;

	// Message to indicate accident in room
	private String deathMessage;

    /**
     *
     * Create a new dark room
     * @param description the description
     * @param shortDescription the short description
     * @param darkDescription the dark description
     * @param darkShortDescription the long description
     */
	public RoomDark(String description, String shortDescription, String darkDescription, String darkShortDescription) {
		this(description, shortDescription, darkDescription, darkShortDescription, true);
	}

    /**
     * Create a new dark room
     * @param description the description
     * @param shortDescription the short description
     * @param darkDescription the dark description
     * @param darkShortDescription the long description
     * @param isDark determines if the room is dark to start
     */
	public RoomDark(String description, String shortDescription, String darkDescription, String darkShortDescription, boolean isDark){
		super(description, shortDescription);

		this.isDark = isDark;
		this.darkDescription = darkDescription;
		this.darkShortDescription = darkShortDescription;
		this.deathMessage = DEATH_MESSAGE;
	}
	// getters & setters
	public boolean isDark() {
		return isDark;
	}
	/**
	 * @param isDark
	 */
	public void setDark(boolean isDark) {
		this.isDark = isDark;
	}
	/**
	 * @return
	 */
	public String deathMessage() {
		return deathMessage;
	}
	/**
	 * @param message
	 */
	public void setDeathMessage(String message) {
		this.deathMessage = message;
	}

	@Override
	public String toString() {
		if(isDark) {
			if(getPlayer().hasLuminousItem()) {
				return super.toString();
			}
			else {
				return darkDescription;
			}
		}
		else {
			return super.toString();
		}
	}

	@Override
	public String description() {
		if (isDark) {
			if (getPlayer().hasLuminousItem()) {
				String s = roomWasVisited ? shortDescription : description + "\n" + visibleItems();
				roomWasVisited = true;
				return s;
			} else {
				String s = roomWasVisited ? darkShortDescription : darkDescription;
				roomWasVisited = true;
				return s;
			}
		} else {
			String s = roomWasVisited ? shortDescription : description + "\n" + visibleItems();
			roomWasVisited = true;
			return s;
		}
	}
}
