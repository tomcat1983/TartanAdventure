package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Valuable;

public class ItemShovel extends Item implements Holdable, Valuable {
    public ItemShovel(String d, String sd, String[] a) {
        super(d, sd, a);
    }

    private Integer value=5;
    @Override
    public int value() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }
}
