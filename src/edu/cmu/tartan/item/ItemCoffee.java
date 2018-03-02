package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Edible;
import edu.cmu.tartan.properties.Valuable;

public class ItemCoffee extends Item implements Edible, Valuable {

    private Integer value=1;
    public ItemCoffee(String s, String sd, String[] a) {
        super(s, sd, a);
    }

    public void eat() {
        System.out.println("You grimace at the taste of black coffee, and put down the mug.");
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
