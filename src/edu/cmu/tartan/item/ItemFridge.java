package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Pushable;
import edu.cmu.tartan.room.RoomObscured;

public class ItemFridge extends Item implements Pushable {

    protected boolean wasPushed;

    public ItemFridge(String s, String sd, String[] a) {
        super(s, sd, a);
        this.wasPushed = false;
        setValue(1);
    }

    // Pushable
    public void push() {
        if (!this.wasPushed) {
            if (this.relatedRoom != null && this.relatedRoom instanceof RoomObscured) {
                ((RoomObscured) this.relatedRoom).setObscured(false);
                System.out.println(((RoomObscured) this.relatedRoom).unobscureMessage());
            }
            this.wasPushed = true;
        }
    }
}