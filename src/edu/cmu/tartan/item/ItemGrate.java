package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Destroyable;
import edu.cmu.tartan.room.RoomObscured;

public class ItemGrate extends Item implements Destroyable {

    public ItemGrate(String s, String sd, String[] a) {
        super(s, sd, a);
        this.destroyMessage = null;
    }

    public void destroy() {
        // set obscured room unobscured
        if (this.relatedRoom != null && this.relatedRoom instanceof RoomObscured) {
            ((RoomObscured) this.relatedRoom).setObscured(false);
        }
    }

    public boolean disappears() {
        return false;
    }

    public void setDestroyMessage(String s) {
        this.destroyMessage = s;
    }

    public void setDisappears(boolean b) {
        this.disappears = b;
    }

    protected boolean disappears;
    protected String destroyMessage;
}
