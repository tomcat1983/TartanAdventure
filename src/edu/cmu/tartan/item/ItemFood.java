package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Edible;
import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Valuable;

public class ItemFood extends Item implements Edible, Holdable, Valuable {

    private Integer value;

    public ItemFood(String s, String sd, String[] a) {
        super(s, sd, a);
    }

    public void eat() {
        System.out.println("Yummy");
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
