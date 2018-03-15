package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Valuable;

public class ItemLadder extends Item implements Holdable {

    public ItemLadder(String s, String sd, String[] a) {
        super(s, sd, a);
        setValue(15);
    }
}
