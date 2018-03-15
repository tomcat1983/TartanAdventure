package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Edible;
import edu.cmu.tartan.properties.Valuable;

public class ItemCoffee extends Item implements Edible, Valuable {

    public ItemCoffee(String s, String sd, String[] a) {

        super(s, sd, a);
        setValue(1);
    }

    public void eat() {
        System.out.println("You grimace at the taste of black coffee, and put down the mug.");
    }

}
