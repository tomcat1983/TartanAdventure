package edu.cmu.tartan.item;

import edu.cmu.tartan.GameInterface.MessageType;
import edu.cmu.tartan.properties.Pushable;
import edu.cmu.tartan.room.RoomObscured;

/**
 * This class for a fridge, which can be pushed.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemFridge extends Item implements Pushable {
	private static final long serialVersionUID = 1L;

	/**
	 *  indicates whether fridge has been pushed
	 */
    private boolean wasPushed;

    /**
     * Constructor for the fridge
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemFridge(String s, String sd, String[] a, String userId) {
        super(s, sd, a, userId);
        this.wasPushed = false;
        setValue(1);
    }

    /**
     * Push the fridge out of the way to reveal an obscured room.
     */
    @Override
    public void push() {
        if (!this.wasPushed) {
            if (this.relatedRoom instanceof RoomObscured) {
                ((RoomObscured) this.relatedRoom).setObscured(false);
                gameInterface.println(userId, MessageType.PRIVATE, ((RoomObscured) this.relatedRoom).unobscureMessage());
            }
            this.wasPushed = true;
        }
    }
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (wasPushed ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ItemFridge other = (ItemFridge) obj;
		return wasPushed == other.wasPushed;
	}
}