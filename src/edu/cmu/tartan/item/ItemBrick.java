package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Valuable;

public class ItemBrick extends Item implements Holdable {

    public ItemBrick(String s, String sd, String[] a) {

        super(s, sd, a);
        setValue(5);
    }
}
