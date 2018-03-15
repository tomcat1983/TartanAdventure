package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.properties.Openable;
import edu.cmu.tartan.room.RoomLockable;

public class ItemLock extends Item implements Hostable, Openable {

    protected Item installedItem;

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
