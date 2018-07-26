package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Chuckable;
import edu.cmu.tartan.properties.Holdable;

/**
 * This class for a brick, which can be held.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemBrick extends Item implements Holdable, Chuckable {

    /**
     * Create a brick
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemBrick(String s, String sd, String[] a) {

        super(s, sd, a);
        setValue(5);
    }

	@Override
	public void chuck() {
		gameInterface.println("~~~~~~~~~throwing!!!");
	}
}
