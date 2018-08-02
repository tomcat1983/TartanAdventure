package edu.cmu.tartan.room;

import edu.cmu.tartan.item.Item;

/**
 * The class for an obscured room. An item is hiding this room
 * from this class.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class RoomObscured extends Room {
	private static final long serialVersionUID = 1L;
	// The item hiding this room
	private Item obscuringItem;
	private boolean isObscured;
    private String obscureMessage;
    private String unobscureMessage;

    /**
     * Create a new obscured room
     * @param description the description
     * @param shortDescription the short description
     * @param obscuringItem The item obscuring this room
     */
	public RoomObscured(String description, String shortDescription, Item obscuringItem) {
		super(description, shortDescription);
		this.obscuringItem = obscuringItem;
		this.isObscured = true;
		this.obscureMessage = null;
		this.unobscureMessage = null;
	}

	//make room first, set obscuringItem later
	public RoomObscured(String description, String shortDescription) {
		this(description, shortDescription, null);
	}

    // Getters & setters
    public Item getObscuringItem() {
		return obscuringItem;
	}

	/**
	 * @param obscuringItem
	 */
	public void setObscuringItem(Item obscuringItem) {
		this.obscuringItem = obscuringItem;
	}
	/**
	 * @return
	 */
	public boolean isObscured() {
		return this.isObscured;
	}
	/**
	 * @param obscured
	 */
	public void setObscured(boolean obscured) {
		this.isObscured = obscured;
	}
	/**
	 * @param s
	 */
	public void setUnobscureMessage(String s) {
		this.unobscureMessage = s;
	}
	/**
	 * @return
	 */
	public String unobscureMessage() {
		return this.unobscureMessage;
	}
	/**
	 * @param s
	 */
	public void setObscureMessage(String s) {
		this.obscureMessage = s;
	}
	/**
	 * @return
	 */
	public String obscureMessage() {
		return this.obscureMessage;
	}

}
