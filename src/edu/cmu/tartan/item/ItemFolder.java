package edu.cmu.tartan.item;

import edu.cmu.tartan.GameInterface.MessageType;
import edu.cmu.tartan.properties.Openable;

/**
 * This class for a folder, which can be opened.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemFolder extends Item implements Openable {

	private static final long serialVersionUID = 1L;
	private String openMessage;

    /**
     * Constructor
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemFolder(String s, String sd, String[] a, String userId) {

        super(s, sd, a, userId);
        setValue(3);
    }

    /**
     * Open the folder
     * @return true when the folder is opened
     */
    @Override
    public Boolean open() {

    	gameInterface.println(userId, MessageType.PRIVATE, this.openMessage);
        return true;
    }

    /**
     * The message to display when the folder is opened.
     * @param o
     */
    public void setOpenMessage(String o) {
        this.openMessage = o;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((openMessage == null) ? 0 : openMessage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj==null || getClass() != obj.getClass() || !super.equals(obj)) {
			return false;
		} else if (this == obj) {
			return true;
		}

		ItemFolder other = (ItemFolder) obj;
		if (openMessage == null) {
			if (other.openMessage != null) {
				return false;
			}
		} else if (!openMessage.equals(other.openMessage)) {
			return false;
		}
		return true;
	}
}
