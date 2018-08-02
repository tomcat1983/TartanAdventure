package edu.cmu.tartan.item;

import edu.cmu.tartan.GameInterface.MessageType;
import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.properties.Openable;

/**
 * This class for a safe, which can hold something and be opened.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemSafe extends Item implements Hostable, Openable {

	private static final long serialVersionUID = 1L;

	// Items installed in this safe
    private Item installedItem = null;

    // The safe's pin, which controls entry
    private Integer pin = null;

    /**
     * @param d
     * @param sd
     * @param a
     * @param userId
     */
    public ItemSafe(String d, String sd, String[] a, String userId) {
        super(d, sd, a, userId);
        installedItem = null;
        pin = null;
        setValue(750);
    }

    /**
     * Set the safe PIN
     * @param pin the pin
     */
    public void setPIN(Integer pin) {
        this.pin = pin;
    }

    /**
     * Unstall the installed item
     * @param i the specific item to uninstall
     * @return true if successful; false otherwise
     */
    @Override
    public boolean uninstall(Item i) {
        if (this.installedItem == null) {
            return false;
        } else if (this.installedItem == i) {
            this.installedItem = null;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Install an item in the safe.
     * @param i the item to install
     */
    @Override
    public boolean install(Item item) {
    	if(installedItem==null) {
            installedItem = item;
    		return true;
    	}
    	return false;
    }

    /**
     * Fetch the installed item
     * @return the installed item
     */
    @Override
	public Item installedItem() {
        return installedItem;
    }

    /**
     * Open the safe using the pin
     * @return true if the safe is successfully opened; false otherwise
     */
    @Override
    public Boolean open() {
        gameInterface.println(userId, MessageType.PRIVATE, "Enter the four-digit PIN number.");
        Integer p = Integer.parseInt(gameInterface.getCommand(userId));
        if (p.intValue() == this.pin.intValue()) {
        	if (this.installedItem != null) {
        		this.installedItem.setVisible(true);
        		gameInterface.println(userId, MessageType.PRIVATE, "The safe door swings open.");
        		gameInterface.println(userId, MessageType.PRIVATE, "You have revealed a '" + this.installedItem.detailDescription() + "'.");
            }
            return true;
        } else {
        	gameInterface.println(userId, MessageType.PRIVATE, "Incorrect PIN.");
        }
        return false;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((installedItem == null) ? 0 : installedItem.hashCode());
		result = prime * result + ((pin == null) ? 0 : pin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj==null || getClass() != obj.getClass() || !super.equals(obj)) {
			return false;
		} else if (this == obj) {
			return true;
		}

		ItemSafe other = (ItemSafe) obj;
		if (pin == null) {
			if (other.pin != null)
				return false;
		} else if (!pin.equals(other.pin)) {
			return false;
		}
		return true;
	}
}
