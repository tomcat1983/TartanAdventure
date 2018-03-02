package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Installable;
import edu.cmu.tartan.properties.Valuable;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;

public class ItemMetalPole extends Item implements Holdable, Installable, Valuable {
    public ItemMetalPole(String s, String sd, String[] a) {
        super(s, sd, a);
    }

    private Integer value=3;
    @Override
    public int value() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }
}
