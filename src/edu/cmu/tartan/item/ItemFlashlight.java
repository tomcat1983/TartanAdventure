package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Installable;
import edu.cmu.tartan.properties.Luminous;
import edu.cmu.tartan.properties.Valuable;

public class ItemFlashlight extends Item implements Holdable, Installable, Luminous {

    public ItemFlashlight(String s, String sd, String[] a) {

        super(s, sd, a);
        setValue(5);
    }
}
