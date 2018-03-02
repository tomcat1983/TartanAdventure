package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Installable;
import edu.cmu.tartan.properties.Valuable;

public class ItemDocument extends Item implements Holdable, Installable, Valuable {
    private Integer value = 50;

    public ItemDocument(String s, String sd, String[] a) {
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
