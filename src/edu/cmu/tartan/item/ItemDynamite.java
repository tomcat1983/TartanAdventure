package edu.cmu.tartan.item;

import edu.cmu.tartan.GameInterface.MessageType;
import edu.cmu.tartan.properties.Explodable;
import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.room.RoomObscured;

/**
 * This class for dynamite, which can be held and explode.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemDynamite extends Item implements Explodable, Holdable {

	// Indicates whether dynamite has been detonated
    private boolean exploded;

    /**
     * Constructor
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemDynamite(String s, String sd, String[] a, String userId) {
        super(s, sd, a, userId);
        this.exploded = false;
        setValue(25);
    }

    /**
     * Explode the dynamite. Can be used to clear the way to a room
     *
     * @return true when the explosion occurs
     */
    @Override
    public Boolean explode() {
        if (!exploded) {
            if (relatedRoom instanceof RoomObscured) {
                ((RoomObscured) relatedRoom).setObscured(false);
                gameInterface.println(userId, MessageType.PRIVATE, ((RoomObscured) relatedRoom).unobscureMessage());
            }
            exploded = true;
            detailDescription = "pile of smithereens";
        } else {
        	gameInterface.println(userId, MessageType.PRIVATE, "The dynamite has already been detonated.");
        }
        return exploded;
    }

    /**
     * @param s
     */
    public void setExplodeMessage(String s) {
        if (this.relatedRoom instanceof RoomObscured) {
            ((RoomObscured) this.relatedRoom).setUnobscureMessage(s);
        }
    }

    /**
     * Has the dynamite been detonated?
     * @return true if the detonation occurred
     */
    public Boolean getExploded() {
        return exploded;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (exploded ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj==null || getClass() != obj.getClass() || !super.equals(obj)) {
			return false;
		} else if (this == obj) {
			return true;
		}

		ItemDynamite other = (ItemDynamite) obj;
		return exploded == other.exploded;
	}
}