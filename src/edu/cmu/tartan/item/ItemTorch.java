package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Luminous;
import edu.cmu.tartan.properties.Valuable;

public class ItemTorch extends Item implements Holdable, Luminous, Valuable {
    public ItemTorch(String d, String sd, String[] a) {
        super(d, sd, a);
    }

    private Integer value=7;
    @Override
    public int value() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }
}
