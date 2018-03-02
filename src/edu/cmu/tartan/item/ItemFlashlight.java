package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Installable;
import edu.cmu.tartan.properties.Luminous;
import edu.cmu.tartan.properties.Valuable;

public class ItemFlashlight extends Item implements Holdable, Installable, Luminous, Valuable {

    private Integer value;

    public ItemFlashlight(String s, String sd, String[] a) {
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
