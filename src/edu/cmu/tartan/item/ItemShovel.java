package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;

/**
 * This class for a shovel, which can be held .
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemShovel extends Item implements Holdable {
    /**
     * @param d
     * @param sd
     * @param a
     * @param userId
     */
    public ItemShovel(String d, String sd, String[] a, String userId) {

        super(d, sd, a, userId);
        setValue(5);
    }
}