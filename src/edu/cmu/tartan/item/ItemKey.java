package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Installable;
import edu.cmu.tartan.properties.Valuable;

public class ItemKey extends Item implements Holdable, Installable {

    public ItemKey(String s, String sd, String[] a) {

        super(s, sd, a);
        setValue(40);
    }
}
