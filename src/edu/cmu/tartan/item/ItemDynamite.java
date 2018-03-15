package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Explodable;
import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Valuable;
import edu.cmu.tartan.room.RoomObscured;

public class ItemDynamite extends Item implements Explodable, Holdable {
    protected boolean exploded;

    public ItemDynamite(String s, String sd, String[] a) {
        super(s, sd, a);
        this.exploded = false;
        setValue(25);
    }

    public Boolean explode() {
        if (!this.exploded) {
            if (this.relatedRoom != null && this.relatedRoom instanceof RoomObscured) {
                ((RoomObscured) this.relatedRoom).setObscured(false);
                System.out.println(((RoomObscured) this.relatedRoom).unobscureMessage());
            }
            this.exploded = true;
            this.detailDescription = "pile of smithereens";
        } else {
            System.out.println("The dynamite has already been detonated.");
        }
        return exploded;
    }

    public void setExplodeMessage(String s) {
        if (this.relatedRoom != null && this.relatedRoom instanceof RoomObscured) {
            ((RoomObscured) this.relatedRoom).setUnobscureMessage(s);
        }
    }

    public Boolean getExploded() {
        return exploded;
    }
}
