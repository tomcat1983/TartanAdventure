package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Valuable;

public class ItemShovel extends Item implements Holdable {
    public ItemShovel(String d, String sd, String[] a) {

        super(d, sd, a);
        setValue(5);
    }
}