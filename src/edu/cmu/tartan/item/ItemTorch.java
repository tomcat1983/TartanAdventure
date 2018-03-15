package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Luminous;
import edu.cmu.tartan.properties.Valuable;

public class ItemTorch extends Item implements Holdable, Luminous {
    public ItemTorch(String d, String sd, String[] a) {

        super(d, sd, a);
        setValue(10);
    }
}