package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Valuable;

public class ItemLadder extends Item implements Holdable, Valuable {

    private Integer value;

    public ItemLadder(String s, String sd, String[] a) {
        super(s, sd, a);
    }

    @Override
    public int value() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }
}
