package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Edible;
import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Valuable;

public class ItemFood extends Item implements Edible, Holdable {

    public ItemFood(String s, String sd, String[] a) {

        super(s, sd, a);
        setValue(3);
    }

    public void eat() {
        System.out.println("Yummy");
    }
}
