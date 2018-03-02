package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Startable;
import edu.cmu.tartan.properties.Wearable;
import edu.cmu.tartan.room.RoomSky;

public class ItemParachute extends Item implements Startable, Wearable {

    public ItemParachute(String s, String sd, String[] a) {
        super(s, sd, a);
    }

    public void start() {
        ((RoomSky) this.relatedRoom).breakFall();
    }
}
