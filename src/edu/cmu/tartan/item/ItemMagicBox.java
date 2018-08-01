package edu.cmu.tartan.item;

import org.eclipse.jdt.annotation.Nullable;

import edu.cmu.tartan.properties.Hostable;

/**
 * This class for a magic box, which can hold something (make it disappear).
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemMagicBox extends Item implements Hostable {

	private static final long serialVersionUID = 1L;

	/**
     * Constructor
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemMagicBox(String s, String sd, String[] a, String userId) {

        super(s, sd, a, userId);
        setValue(7);
    }

    /**
     * Install an item
     * @param i the item to install
     */
    @Override
    public boolean install(@Nullable Item i) {
    	if(i==null) {
    		return false;
    	} 
        // items fall into black hole
    	i.setVisible(false);
    	return true;
    }

    /**
     * Uninstall an item
     * @param i the item to uninstall
     */
    @Override
    public boolean uninstall(@Nullable Item i) {
        return false;
    }

    /**
     * Fetch the installed item
     */
    @Override
    public @Nullable Item installedItem() {
        return null;
    }
}
