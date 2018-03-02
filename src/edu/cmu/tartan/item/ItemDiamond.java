package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Installable;
import edu.cmu.tartan.properties.Valuable;

public class ItemDiamond extends Item implements Holdable, Installable, Valuable {

    public ItemDiamond(String s, String sd, String[] a) {
        super(s, sd, a);
    }

    public int value() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    protected int value;
}
