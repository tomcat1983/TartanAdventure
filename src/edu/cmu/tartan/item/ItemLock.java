package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.properties.Openable;
import edu.cmu.tartan.room.RoomLockable;

/**
 * This class for a brick, which can hold a key and be opened.
 * <p/>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemLock extends Item implements Hostable, Openable {

    private Item installedItem;

    /**
     * Constructor
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemLock(String s, String sd, String[] a) {

        super(s, sd, a);
        setValue(100);
    }

    public void install(Item i) {
        this.installedItem = i;
    }

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

    public Item installedItem() {
        return this.installedItem;
    }

    @Override
    public Boolean open() {
        if (this.relatedRoom != null && this.relatedRoom instanceof RoomLockable) {
            ((RoomLockable) this.relatedRoom).unlock(this.installedItem);
            return true;
        }
        return false;
    }
}
