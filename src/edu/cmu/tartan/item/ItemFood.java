package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Edible;
import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Valuable;

/**
 * This class for food, which can be held and eaten.
 * <p/>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemFood extends Item implements Edible, Holdable {

    /**
     * Constructor
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemFood(String s, String sd, String[] a) {

        super(s, sd, a);
        setValue(3);
    }

    @Override
    public void eat() {
        System.out.println("Yummy");
    }
}
