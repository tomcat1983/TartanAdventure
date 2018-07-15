package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.properties.Openable;

import java.util.Scanner;

/**
 * This class for a safe, which can hold something and be opened.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemSafe extends Item implements Hostable, Openable {

    // Items installed in this safe
    private Item installedItem = null;

    // The safe's pin, which controls entry
    private Integer pin = null;

    public ItemSafe(String d, String sd, String[] a) {
        super(d, sd, a);
        this.installedItem = null;
        this.pin = null;
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
     * Install an item in the safe.
     * @param i the item to install
     */
    @Override
    public void install(Item i) {
        this.installedItem = i;
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
     * Fetch the installed item
     * @return the installed item
     */
    public Item installedItem() {
        return this.installedItem;
    }

    /**
     * Open the safe using the pin
     * @return true if the safe is successfully opened; false otherwise
     */
    @Override
    public Boolean open() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the four-digit PIN number.");
        Integer p = Integer.parseInt(s.nextLine());
        if (p.intValue() == this.pin.intValue()) {
        	if (this.installedItem != null) {
        		this.installedItem.setVisible(true);
        		System.out.println("The safe door swings open.");
                System.out.println("You have revealed a '" + this.installedItem.detailDescription() + "'.");
            }
            return true;
        } else {
            System.out.println("Incorrect PIN.");
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
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemSafe other = (ItemSafe) obj;
		if (installedItem == null) {
			if (other.installedItem != null)
				return false;
		} else if (!installedItem.equals(other.installedItem)) {
			return false;
		}
		if (pin == null) {
			if (other.pin != null)
				return false;
		} else if (!pin.equals(other.pin)) {
			return false;
		}
		
		return true;
	}
}
