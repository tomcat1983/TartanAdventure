package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Edible;
import edu.cmu.tartan.properties.Holdable;

public class ItemFood extends Item implements Edible, Holdable {

    public ItemFood(String s, String sd, String[] a) {
        super(s, sd, a);
    }

    public void eat() {
        System.out.println("Yummy");
    }
}
