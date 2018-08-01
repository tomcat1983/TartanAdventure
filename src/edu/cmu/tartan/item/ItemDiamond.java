package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Installable;

/**
 * This class for a diam, which can be held and placed in something.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemDiamond extends Item implements Holdable, Installable {

	private static final long serialVersionUID = 1L;

	/**
     * Constructor for a diamond
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemDiamond(String s, String sd, String[] a, String userId) {

        super(s, sd, a, userId);
        setValue(1000);
    }
}