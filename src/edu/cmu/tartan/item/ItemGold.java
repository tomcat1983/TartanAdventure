package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Installable;
import edu.cmu.tartan.properties.Valuable;

public class ItemGold extends Item implements Installable, Holdable {

    public ItemGold(String s, String sd, String[] a) {
        super(s, sd, a);
        setValue(500);
    }
}
