package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Luminous;

public class ItemDeskLight extends Item implements Holdable, Luminous {

    public ItemDeskLight(String s, String sd, String[] a) {
        super(s, sd, a);
        setValue(10);
    }
}
