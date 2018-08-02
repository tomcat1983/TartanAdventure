package edu.cmu.tartan.item;

import edu.cmu.tartan.GameInterface.MessageType;
import edu.cmu.tartan.properties.Edible;

/**
 * This class for coffee cup, which can be eaten.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemCoffee extends Item implements Edible {

	private static final long serialVersionUID = 1L;

	/**
     * Constructor
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemCoffee(String s, String sd, String[] a, String userId) {
        super(s, sd, a, userId);
        setValue(1);
    }

    @Override
    public void eat() {
        gameInterface.println(userId, MessageType.PRIVATE, "You grimace at the taste of black coffee, and put down the mug.");
    }

}
