package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Installable;
import edu.cmu.tartan.properties.Valuable;

public class ItemDiamond extends Item implements Holdable, Installable {

    public ItemDiamond(String s, String sd, String[] a) {

        super(s, sd, a);
        setValue(1000);
    }
}