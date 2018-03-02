package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Installable;
import edu.cmu.tartan.properties.Valuable;

public class ItemGold extends Item implements Installable, Holdable, Valuable {

    public ItemGold(String s, String sd, String[] a) {
        super(s, sd, a);
        this.value = 0;
    }

    public int value() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    protected int value;
}
