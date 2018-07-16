package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Destroyable;
import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Hostable;

/**
 * A clay pot can be destroyed, held, and host other objects
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemClayPot extends Item implements Destroyable, Holdable, Hostable {

	private String destroyMessage;
    private Item installedItem;
    private boolean disappears;

    /**
     * Create a new clay pot
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemClayPot(String s, String sd, String[] a) {
        super(s, sd, a);
        this.installedItem = null;
        setValue(3);
    }

    /**
     * Message to display when breaking the pot
     * @param s the message
     */
    @Override
    public void setDestroyMessage(String s) {
        this.destroyMessage = s;
    }

    /**
     * Break the pot
     */
    @Override
    public void destroy() {
        System.out.println(destroyMessage);
    }

    /**
     * Sets whether pot should disappear
     * @param b set to true if the item should disappear
     */
    @Override
    public void setDisappears(boolean b) {
        this.disappears = b;
    }

    /**
     * Make the pot vanish
     * @return
     */
    @Override
    public boolean disappears() {
        return this.disappears;
    }

    /**
     * Install an item in the pot
     * @param i the item to install
     */
    @Override
    public void install(Item i) {
        this.installedItem = i;
    }

    /**
     * Uninstall an item
     * @param i the item
     * @return true if uninstalled; false otherwise
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
     * Fetch the item in the pot
     * @return the item installed in the pot
     */
    @Override
    public Item installedItem() {
        return this.installedItem;
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((destroyMessage == null) ? 0 : destroyMessage.hashCode());
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
		if (obj==null || getClass() != obj.getClass()) {
			return false;
		}
		ItemClayPot other = (ItemClayPot) obj;
		if (destroyMessage == null) {
			if (other.destroyMessage != null) {
				return false;
			}
		} else if (!destroyMessage.equals(other.destroyMessage)) {
			return false;
		}
		return true;
	}
}
