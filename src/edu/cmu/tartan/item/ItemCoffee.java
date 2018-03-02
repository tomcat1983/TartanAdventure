package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Edible;

public class ItemCoffee extends Item implements Edible {

    public ItemCoffee(String s, String sd, String[] a) {
        super(s, sd, a);
    }

    public void eat() {
        System.out.println("You grimace at the taste of black coffee, and put down the mug.");
    }
}
