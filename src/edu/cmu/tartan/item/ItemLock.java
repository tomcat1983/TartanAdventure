package edu.cmu.tartan.item;

import edu.cmu.tartan.GameInterface.MessageType;
import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.properties.Openable;
import edu.cmu.tartan.room.RoomLockable;

/**
 * This class for a brick, which can hold a key and be opened.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemLock extends Item implements Hostable, Openable {
	private static final long serialVersionUID = 1L;
	private Item installedItem;

    /**
     * Constructor
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemLock(String s, String sd, String[] a, String userId) {

        super(s, sd, a, userId);
        setValue(100);
    }

    /**
     * Install an item
     * @param i the item to install
     */
    @Override
    public boolean install(Item i) {
    	if (!(i instanceof ItemKey)) return false;
        
    	if(installedItem==null) {
        	installedItem = i;
        	return true;
        }
        return false;
    }

    /**
     * Uninstall an installed item
     * @param i the item to uninstall
     * @return
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
     * Fetch the installed items
     * @return
     */
    @Override
    public Item installedItem() {
        return this.installedItem;
    }

    /**
     * Open the lock to the locked room. Note the key must be installed in the lock to open
     * @return true if opened; false otherwise
     */
    @Override
    public Boolean open() {
        if (installedItem instanceof ItemKey && this.relatedRoom instanceof RoomLockable) {
            if(((RoomLockable) this.relatedRoom).unlock(this.installedItem)) {
                gameInterface.println(userId, MessageType.PRIVATE, ((RoomLockable) this.relatedRoom).getUnlockMessage());
                return true;
            } else {
            	gameInterface.println(userId, MessageType.PRIVATE, "This key doesn't seem to fit");
            	return false;
            }
        }
        return false;
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((installedItem == null) ? 0 : installedItem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj==null || getClass() != obj.getClass() || !super.equals(obj)) {
			return false;
		} else if (this == obj) {
			return true;
		}
		
		ItemLock other = (ItemLock) obj;
		if (installedItem == null) {
			if (other.installedItem != null) {
				return false;
			}
		} else if (!installedItem.equals(other.installedItem)) {
			return false;
		}
		return true;
	}
}
