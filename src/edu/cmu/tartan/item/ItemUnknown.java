package edu.cmu.tartan.item;

/**
 * Unknown item.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemUnknown extends Item {

	private static final long serialVersionUID = 1L;

	/**
	 * @param s
	 * @param sd
	 * @param a
	 * @param userId
	 */
	public ItemUnknown(String s, String sd, String[] a, String userId) {

        super(s, sd, a, userId);
        setValue(0);
    }
}
